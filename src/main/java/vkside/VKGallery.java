package vkside;


import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;
import starter.Starter;
import tgside.handlers.TGGallery;
import vkside.exceptions.NoSuchAlbumException;

import java.util.*;

public class VKGallery {
    public TGGallery handler;
    private static VKMain vkMain = Starter.vkMain;
    private VkApiClient vk = Starter.vkMain.getVk();
    private UserActor actor = vkMain.getActor();
    private Integer groupId = vkMain.getGroupID();
    private String userName;

    public VKGallery(TGGallery handler) {
        this.handler = handler;
        userName =  handler.getMsg().getChat().getUserName();
    }

    public Map<String, Integer> getAlbumsMap() {
        Map<String, Integer> answer = new HashMap<>();
        List<String> listOfAlbums = getListOfAlbums();
        for (String albumName : listOfAlbums) {
            int countOfPhotosInAlbum = 0;
            try {
                countOfPhotosInAlbum = getAllPhotosFromAlbum().execute().getItems().size();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
            answer.put(albumName, countOfPhotosInAlbum);
        }
        return answer;
    }

    private List<String> getListOfAlbums() {
        List<String> listOfAlbums = new ArrayList<>();
        try {
            vk.photos().getAlbums(actor).ownerId(-groupId).execute().getItems().forEach(x -> listOfAlbums.add(x.getTitle()));
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return listOfAlbums;
    }

    private PhotosGetQuery getAllPhotosFromAlbum() {
        String albumId = Integer.toString(getAlbumId());
        return vk.photos().get(actor).ownerId(-groupId).albumId(albumId);
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
        return -1;
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

    public String getAlbumLink(String albumName) throws NoSuchAlbumException {
        int albomId = getAlbumId(albumName);
        String albumLink = "empty";
        if (albomId != -1) {
            albumLink = "vk.com/album-" + groupId + "_" + albomId;
        } else throw new NoSuchAlbumException();
        return albumLink;
    }
}
