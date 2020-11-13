/*
package living.tanach.api.config;

import dev.sanda.apifi.test_utils.TestableGraphQLService;
import living.tanach.api.model.Verse;
import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ScriptsExecutor {

    private final S3Service s3Service;
    private final TestableGraphQLService<Verse> verseApi;

    @PostConstruct
    private void execute(){
        executeManualMediaContentUpload();
    }

    private static final String mediaContentPath = "/home/dave01/Downloads/17/Maayan";
    private void executeManualMediaContentUpload(){
        // get all media files
        val mediaContentDir = Paths.get(mediaContentPath).toFile();
        // get all relevant verses
        int i = 9;
        // associate tags & content

        // upload as objects to S3
    }

}
*/
