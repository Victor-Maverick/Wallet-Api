package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.premblyUseCases.VerifyNinAndFaceUseCase;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PremblyUserService implements VerifyNinAndFaceUseCase {

    private final Cloudinary cloudinary;
    private final UserOutputPort userOutputPort;

    @Value("${prembly.api.base-url}")
    private String baseUrl;

    @Value("${prembly.api.endpoint}")
    private String endpointUrl;

    @Value("${prembly.api.key}")
    private String premblyApiKey;


    public PremblyUserService(Cloudinary cloudinary, UserOutputPort userOutputPort) {
        this.cloudinary = cloudinary;
        this.userOutputPort = userOutputPort;
    }


    @Override
    public String verifyNinAndFace(VerificationRequest verificationRequest) throws IOException {
        userOutputPort.getByEmail(verificationRequest.getEmail());
        OkHttpClient client = new OkHttpClient();
        String imageUrl = uploadImage(verificationRequest.getImage());
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String formContent = "image=" + imageUrl + "&number=" + verificationRequest.getNin();
        RequestBody requestBody = RequestBody.create(formContent, mediaType);

        Request request = buildRequestBody(requestBody);

        try (Response response = client.newCall(request).execute()) {
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
