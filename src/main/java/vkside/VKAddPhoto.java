package vkside;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import starter.Starter;
import tgside.handlers.TGPhotoHandler;
import utils.URLReader;

import java.io.File;
import java.util.Date;
import java.util.List;

public class VKAddPhoto {
    public TGPhotoHandler handler;
    private static VKMain vkMain = Starter.vkMain;
    private VkApiClient vk = Starter.vkMain.getVk();
    private UserActor actor = vkMain.getActor();
    private Integer groupId = vkMain.getGroupID();


    public void addPhoto(String uri, TGPhotoHandler handler) {
        String userName = handler.getMsg().getChat().getUserName();
        File newPhoto = new File("/Users/mhlv/Documents/Photos" ,userName + new Date().getTime() + ".jpg" );
        URLReader.copyURLToFile(uri, newPhoto);
        this.handler = handler;
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

    private int getAlbumId(String albumName) {
        GetAlbumsResponse albums = null;
        try {
            albums = vk.photos().getAlbums(actor).ownerId(-groupId).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < albums.getItems().size(); i++) {
            if (albums.getItems().get(i).getTitle().equals(albumName)) {
                return albums.getItems().get(i).getId();
            }
        }
        return createAlbum(albumName);
    }

    private int createAlbum(String albumName) {
        int newAlbumId = -100;
        try {
            newAlbumId = vk.photos().createAlbum(actor, albumName).groupId(groupId).description("photos by " + albumName).execute().getId();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return newAlbumId;
    }

}
