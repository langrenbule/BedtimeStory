package com.deity.bedtimestory.dao;

import com.deity.bedtimestory.entity.NewItem;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Deity on 2016/10/24.
 */

public interface NewItemDao {
    void addNewItemEntities(List<NewItem> newItems);
    void addNewItemEntity(NewItem newItem);
    RealmResults<NewItemEntity> queryNewItemEntities(int request);
}
