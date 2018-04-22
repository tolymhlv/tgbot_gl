package tgside.handlers.ents;

public class Photo {
    private String file_id;
    private Integer width;
    private Integer height;
    private Integer file_size;
    private String file_path;
    public Photo() {
    }

    public boolean hasFilePath() {
        return file_path != null && !file_path.isEmpty();
    }

    public String getFileId() {
        return file_id;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getFileSize() {
        return file_size;
    }

    public String getFilePath() {
        return file_path;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "fileId='" + file_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fileSize=" + file_size +
                '}';
    }
}
