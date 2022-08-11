package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class Pet{
    private List<String> photoUrls;
    private String name;
    private int id;
    private Category category;
    private List<TagsItem> tags;
    private String status;
}