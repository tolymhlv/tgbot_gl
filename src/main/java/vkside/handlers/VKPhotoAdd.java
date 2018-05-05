package vkside.handlers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import tgside.handlers.TGPhotoAdd;
import utils.URLReader;

import java.io.File;
import java.util.Date;
import java.util.List;

public class VKPhotoAdd extends VKHandler {

    public VKPhotoAdd(TGPhotoAdd handler) {
        this.handler = handler;
    }

    public void addPhoto(String uri) {
        String userName = handler.getMsg().getChat().getUserName();
        File newPhoto = new File("/Users/mhlv/Documents/Photos" ,userName + new Date().getTime() + ".jpg" );
        URLReader.copyURLToFile(uri, newPhoto);
        PhotoUpload photoUpload = getServerUpload(handler.getMsg().getChat().getUserName());
        try {
            PhotoUploadResponse uploadResponse = vk.upload().photo(photoUpload.getUploadUrl(), newPhoto).execute();
            List<Photo> photoList = vk.photos().save(actor).groupId(groupId)
                    .server(uploadResponse.getServer())
                    .hash(uploadResponse.getHash())
                    .albumId(getAlbumId(userName))
                    .photosList(uploadResponse.getPhotosList())
                    .execute();

        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        handler.sendMessage("Photo has been upload");
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
