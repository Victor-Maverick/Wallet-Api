package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.premblyUseCases.VerifyNinAndFaceUseCase;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class PremblyUserService implements VerifyNinAndFaceUseCase {

    private static final Logger log = LoggerFactory.getLogger(PremblyUserService.class);
    private final Cloudinary cloudinary;

    @Value("${prembly.api.base-url}")
    private String baseUrl;

    @Value("${prembly.api.endpoint}")
    private String endpointUrl;

    @Value("${prembly.api.key}")
    private String premblyApiKey;


    public PremblyUserService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String verifyNinAndFace(VerificationRequest verificationRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        log.info("prembly key:"+premblyApiKey);
        String imageUrl = uploadImage(verificationRequest.getImageUrl());
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String formContent = "image=" + imageUrl + "&number=" + verificationRequest.getNin();
        RequestBody requestBody = RequestBody.create(formContent, mediaType);

        Request request = new Request.Builder()
                .url(baseUrl + "/" + endpointUrl)
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("x_api_key", premblyApiKey)
                .addHeader("app_id", "piggy_app")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    private String uploadImage(String filePath) throws IOException {
        File file = new File(filePath);
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }
}
