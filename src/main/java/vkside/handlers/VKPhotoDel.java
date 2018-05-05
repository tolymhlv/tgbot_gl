package vkside.handlers;


import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import tgside.handlers.TGPhotoDel;
import vkside.exceptions.NoSuchPhotoException;

import java.io.File;

public class VKPhotoDel extends VKHandler {

    public VKPhotoDel(TGPhotoDel handler) {
        this.handler = handler;
        userName = handler.getMsg().getChat().getUserName();
        if (userName == null) {
            userName = String.valueOf(handler.getMsg().getChat().getId());
        }
    }

    public boolean deleteLastPhoto() throws NoSuchPhotoException {
        return deletePhoto(getLastPhotoId());
    }

    public File getLastPhotoPrev() throws NoSuchPhotoException {
        return getPhoto(getLastPhotoPrevUrl());
    }
    private String getLastPhotoPrevUrl() throws NoSuchPhotoException {
        String url = null;
        String albumId = Integer.toString(getAlbumId(userName));
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

    private int getLastPhotoId() throws NoSuchPhotoException {
        String albumId = Integer.toString(getAlbumId(userName));
        int id = 0;
        try {
            if (!isAlbumEmpty(userName)) {
                id = getAllPhotosFromAlbum(userName).rev(true).execute().getItems().get(0).getId();
            } else throw new NoSuchPhotoException("Album '" + userName + "'is empty.");
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return id;
    }
}
