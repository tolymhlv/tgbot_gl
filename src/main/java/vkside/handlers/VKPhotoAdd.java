package vkside.handlers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import starter.Starter;
import tgside.handlers.TGPhotoAdd;
import utils.URLReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class VKPhotoAdd extends VKHandler {

    public VKPhotoAdd(TGPhotoAdd handler) {
        this.handler = handler;
        if (userName == null) userName = handler.getMsg().getChat().getUserName();
        if (userName == null) userName = String.valueOf(handler.getMsg().getChat().getId());
    }

    public void addPhoto(String uri) {
        File tempPhoto = new File("./src/main/resourses/photos/" + userName + new Date().getTime() + ".jpg");
        URLReader.copyURLToFile(uri, tempPhoto);
        PhotoUpload photoUpload = getServerUpload(userName);
        try {
            PhotoUploadResponse uploadResponse = vk.upload().photo(photoUpload.getUploadUrl(), tempPhoto).execute();
            vk.photos().save(actor).groupId(groupId)
                    .server(uploadResponse.getServer())
                    .hash(uploadResponse.getHash())
                    .albumId(getAlbumId(userName))
                    .photosList(uploadResponse.getPhotosList())
                    .caption("" + new Date().getTime())
                    .execute();
            handler.sendMessage("Photo has been upload");
        } catch (ApiException | ClientException e) {
            handler.sendMessage("Upload photo error");
            e.printStackTrace();
        } finally {
            tempPhoto.delete();
        }
    }

    private PhotoUpload getServerUpload(String userName) {
        PhotoUpload uploadUrl = null;
        try {
           uploadUrl = vk.photos().getUploadServer(actor).albumId(getAlbumId(userName)).groupId(groupId).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return uploadUrl;
    }
}
