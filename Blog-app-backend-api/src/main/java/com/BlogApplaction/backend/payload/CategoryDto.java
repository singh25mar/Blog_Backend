package com.BlogApplaction.backend.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {


    private Integer categoryId;
    @NotEmpty
    @Size(min = 4,message = "Min size of category title is 4")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10, message = "min size of cateogry desc is 10")
    private String categoryDescription;
}
