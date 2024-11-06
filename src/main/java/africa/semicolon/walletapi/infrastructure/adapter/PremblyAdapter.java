package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.IdentityVerificationPort;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PremblyAdapter implements IdentityVerificationPort {
    private final Cloudinary cloudinary;
    private final UserOutputPort userOutputPort;

    @Value("${prembly.api.base-url}")
    private String baseUrl;

    @Value("${prembly.api.endpoint}")
    private String endpointUrl;

    @Value("${prembly.api.key}")
    private String premblyApiKey;

    public PremblyAdapter(Cloudinary cloudinary, UserOutputPort userOutputPort) {
        this.cloudinary = cloudinary;
        this.userOutputPort = userOutputPort;
    }

    @Override
    public String verifyNinAndFace(VerificationRequest request) throws IOException {
        userOutputPort.getByEmail(request.getEmail());
        OkHttpClient client = new OkHttpClient();
        String imageUrl = uploadImage(request.getImage());
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String formContent = "image=" + imageUrl + "&number=" + request.getNin();
        RequestBody requestBody = RequestBody.create(formContent, mediaType);

        Request buildRequestBody = buildRequestBody(requestBody);

        try (Response response = client.newCall(buildRequestBody).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    private @NotNull Request buildRequestBody(RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(baseUrl + "/" + endpointUrl)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("x_api_key", premblyApiKey)
                .addHeader("app_id", "piggy_app")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return request;
    }

    private String uploadImage(MultipartFile image) throws IOException {
        File tempFile = File.createTempFile("temp", image.getOriginalFilename());
        image.transferTo(tempFile);
        Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
        tempFile.delete();
        return (String) uploadResult.get("secure_url");
    }

}
