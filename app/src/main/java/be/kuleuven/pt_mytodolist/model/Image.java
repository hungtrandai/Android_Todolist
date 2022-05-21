package be.kuleuven.pt_mytodolist.model;

public class Image {
    private int imageId;
    private String imageName;
    private String imageContent;

    public Image() {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageContent = imageContent;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
