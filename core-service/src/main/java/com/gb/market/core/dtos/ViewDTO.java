package com.gb.market.core.dtos;

import com.gb.market.core.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ViewDTO {
    private String sortBy;
    private String sortType;
    private int currentPage;
    private int maxItemsOnThePage;
    protected List<Integer> pages;
    private List<Product> productList;
}
