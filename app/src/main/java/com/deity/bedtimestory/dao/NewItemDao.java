package com.deity.bedtimestory.dao;

import com.deity.bedtimestory.entity.NewItem;

import java.util.List;

/**
 * Created by Deity on 2016/10/24.
 */

public interface NewItemDao {
    void addNewItemEntities(List<NewItem> newItems);
    void addNewItemEntity(NewItem newItem);
    List<NewItemEntity> queryNewItemEntities(int request,int currentPage);
}
