package vkside;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetAlbumsResponse;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import tgside.handlers.TGPhotoHandler;
import java.io.File;
import java.util.List;

public class VKAdd {
    public TGPhotoHandler handler;

    private  VkApiClient vk = VKMain.getVk();

    public void addPhoto(String uri, TGPhotoHandler handler) {
        this.handler = handler;
        PhotoUpload photoUpload = getServerUpload(handler.getMsg().getChat().getUserName());
        try {
            PhotoUploadResponse uploadResponse = vk.upload().photo(photoUpload.getUploadUrl(), new File(uri)).execute();
            List<Photo> photoList = vk.photos().save(VKMain.getActor()).groupId(VKMain.getGroupId())
                    .server(uploadResponse.getServer())
                    .hash(uploadResponse.getHash())
                    .albumId(getAlbumId(handler.getMsg().getChat().getUserName()))
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
           uploadUrl = vk.photos().getUploadServer(VKMain.getActor()).albumId(getAlbumId(userName)).groupId(VKMain.getGroupId()).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return uploadUrl;
    }

    private int getAlbumId(String albumName) {
        GetAlbumsResponse albums = null;
        try {
            albums = vk.photos().getAlbums(VKMain.getActor()).ownerId(-VKMain.getGroupId()).execute();
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
            newAlbumId = vk.photos().createAlbum(VKMain.getActor(), albumName).groupId(VKMain.getGroupId()).description("photos by " + albumName).execute().getId();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return newAlbumId;
    }

}
