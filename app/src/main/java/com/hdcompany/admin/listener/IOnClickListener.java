package com.hdcompany.admin.listener;

import com.hdcompany.admin.model.Product;
import com.hdcompany.admin.model.SportClothes;

public interface IOnClickListener {
    void onClickItemProduct(Product product);
    void onClickItemSportClothes(SportClothes sportClothes);
}
