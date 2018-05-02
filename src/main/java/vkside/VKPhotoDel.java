package vkside;


import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import com.vk.api.sdk.queries.photos.PhotosGetAlbumsQuery;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;
import exceptions.PhotoListIsEmptyException;
import starter.Starter;
import tgside.handlers.TGPhotoAdd;
import tgside.handlers.TGPhotoDel;
import utils.URLReader;

import java.io.File;
import java.util.Date;
import java.util.List;

public class VKPhotoDel {
    public TGPhotoDel handler;
    private static VKMain vkMain = Starter.vkMain;
    private VkApiClient vk = Starter.vkMain.getVk();
    private UserActor actor = vkMain.getActor();
    private Integer groupId = vkMain.getGroupID();
    private String userName;

    public VKPhotoDel(TGPhotoDel handler) {
        this.handler = handler;
        userName =  handler.getMsg().getChat().getUserName();
    }

    private boolean deletePhoto(int photoId) throws PhotoListIsEmptyException {
        boolean wasPhotoDeleted = false;
        if (isAlbumEmpty()) {
            throw new PhotoListIsEmptyException(getAlbumId() + " / " + photoId);
        }
        try {
            wasPhotoDeleted = vk.photos().delete(actor, photoId).ownerId(-groupId).execute().getValue() == 1;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return wasPhotoDeleted;
    }

    public boolean deleteLastPhoto() throws PhotoListIsEmptyException {
        return deletePhoto(getLastPhotoId());
    }

    public File getLastPhoto() {
        String userName = handler.getMsg().getChat().getUserName();
        File lastPhoto = new File("/Users/mhlv/Documents/Photos" , userName + new Date().getTime()
                + ".jpg" );
        String lastPhotoUrl = getLastPhotoUrl();
        if (lastPhotoUrl != null) {
            URLReader.copyURLToFile(lastPhotoUrl, lastPhoto);
            return lastPhoto;
        } else {
            return null;
        }

    }
    private String getLastPhotoUrl() {
        String albumName = userName;
        String url = null;
        String albumId = Integer.toString(getAlbumId());
        int lastPhotoId = getLastPhotoId();
        try {
            if (lastPhotoId > 0)
                url = vk.photos().get(actor).ownerId(-groupId).albumId(albumId).rev(true).execute()
                        .getItems().get(0).getPhoto130();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return url;
    }

    private int getLastPhotoId() {
        String albumId = Integer.toString(getAlbumId());
        int id = 0;
        try {
            int size = getAllPhotosFromAlbum().rev(true).execute().getItems().size();
            if (size > 0) {
                id = getAllPhotosFromAlbum().rev(true).execute().getItems().get(0).getId();
            } else throw new NullPointerException();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        } catch (NullPointerException s) {
            return -1;
        }
        return id;
    }

    private int getAlbumId() {
        String albumName = userName;
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
        return createAlbum();
    }

    private int createAlbum() {
        String albumName = userName;
        int newAlbumId = -100;
        try {
            newAlbumId = vk.photos().createAlbum(actor, albumName).groupId(groupId)
                    .description("photos by " + albumName).execute().getId();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return newAlbumId;
    }

    private boolean isAlbumEmpty() {
        try {
            return getAllPhotosFromAlbum().execute().getItems().size() >= 0;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PhotosGetQuery getAllPhotosFromAlbum() {
        String albumId = Integer.toString(getAlbumId());
        return vk.photos().get(actor).ownerId(-groupId).albumId(albumId);
    }
}
