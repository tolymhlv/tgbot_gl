package vkside.handlers;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.PhotoAlbumFull;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;
import starter.Starter;
import tgside.handlers.TGHandler;
import utils.URLReader;
import vkside.exceptions.NoSuchPhotoException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class VKHandler {
    TGHandler handler;
    VkApiClient vk = Starter.getVkMain().getVk();
    UserActor actor = Starter.getVkMain().getActor();
    Integer groupId = Starter.getVkMain().getVkGroupId();
    String userName;

    int getAlbumId(String albumName) {
        GetAlbumsResponse albums = null;
        try {
            albums = vk.photos().getAlbums(actor).ownerId(-groupId).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        if (albums != null) {
            ArrayList<PhotoAlbumFull> albumsList = new ArrayList<>(albums.getItems());
            for (PhotoAlbumFull album : albumsList) {
                if (album.getTitle().equals(albumName))
                    return album.getId();
            }
        }
        return createAlbum(albumName);
    }

    private int createAlbum(String albumName) {
        try {
            return vk.photos().createAlbum(actor, albumName).groupId(groupId)
                    .description("photos by " + albumName).execute().getId();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return -1;

    }

    File getPhoto(String photoUrl) throws NoSuchPhotoException {
        File tempPhoto = new File("./src/main/resources/photos/" + userName + new Date().getTime() + ".jpg");
        if (photoUrl != null)
            return URLReader.copyURLToFile(photoUrl, tempPhoto);
        else throw new NoSuchPhotoException();
    }

    boolean deletePhoto(int photoId) throws NoSuchPhotoException {
        boolean wasPhotoDeleted = false;
        if (isAlbumEmpty(userName)) throw new NoSuchPhotoException("Album '" + userName + "'is empty.");
        try {
            wasPhotoDeleted = vk.photos().delete(actor, photoId).ownerId(-groupId).execute().getValue() == 1;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        if (wasPhotoDeleted) System.out.println("Photo '" + photoId +"' from album '" + userName + "' has been deleted");
        return wasPhotoDeleted;
    }

    boolean isAlbumEmpty(String albumName) {
        try {
            return getAllPhotosFromAlbum(albumName).execute().getItems().size() <= 0;
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    PhotosGetQuery getAllPhotosFromAlbum(String albumName) {
        String albumId = Integer.toString(getAlbumId(albumName));
        return vk.photos().get(actor).ownerId(-groupId).albumId(albumId);
    }

    List<String> getListOfAlbums() {
        List<String> listOfAlbums = new ArrayList<>();
        try {
            vk.photos().getAlbums(actor).ownerId(-groupId).execute().getItems()
                    .forEach(x -> listOfAlbums.add(x.getTitle()));
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return listOfAlbums;
    }
}
