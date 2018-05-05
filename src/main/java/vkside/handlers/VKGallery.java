package vkside.handlers;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.PhotoAlbumFull;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;
import starter.Starter;
import tgside.handlers.TGGallery;
import vkside.exceptions.NoSuchAlbumException;

import java.util.*;

public class VKGallery extends VKHandler {

    public Map<String, Integer> getAlbumsMap() {
        Map<String, Integer> answer = new HashMap<>();
        List<String> listOfAlbums = getListOfAlbums();
        for (String albumName : listOfAlbums) {
            int countOfPhotosInAlbum = 0;
            try {
                countOfPhotosInAlbum = getAllPhotosFromAlbum(albumName).execute().getItems().size();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
            answer.put(albumName, countOfPhotosInAlbum);
        }
        return answer;
    }

    public String getAlbumLink(String albumName) throws NoSuchAlbumException {
        int albumId = getAlbumId(albumName);
        if (albumId != -1) return "vk.com/album-" + groupId + "_" + albumId;
        else throw new NoSuchAlbumException();
    }
}
