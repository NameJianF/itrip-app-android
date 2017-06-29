package live.itrip.common.files.upload.model;

import java.io.File;

/**
 * Created by Feng on 2017/6/29.
 * Detail
 */
public class UploadModel {

    private File file;

    public UploadModel(File file) {
        this.file = file;
    }

    public UploadModel() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
