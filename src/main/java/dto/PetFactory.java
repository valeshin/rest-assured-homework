package dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PetFactory {

    public Pet createPet() {
        Random rand = new Random();
        int petId = (int) (new Date().getTime() % 1000000000);
        Category category = Category.builder().id(rand.nextInt(Integer.MAX_VALUE)).name("ValeryOtusTest").build();
        TagsItem tagsItem1 = TagsItem.builder().id(rand.nextInt(Integer.MAX_VALUE)).name("Pumpkin").build();
        TagsItem tagsItem2 = TagsItem.builder().id(rand.nextInt(Integer.MAX_VALUE)).name("Potato").build();
        List<String> protoUrls = Arrays.asList("/proto/url1", "/proto/url2", "/proto/url3");
        List<TagsItem> tagsItems = Arrays.asList(tagsItem1, tagsItem2);
        return Pet.builder()
                .id(petId)
                .category(category)
                .name("Valera")
                .photoUrls(protoUrls)
                .tags(tagsItems)
                .status("pending")
                .build();
    }
}
