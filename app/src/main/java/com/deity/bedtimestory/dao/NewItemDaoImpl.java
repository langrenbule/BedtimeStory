package com.deity.bedtimestory.dao;

import com.deity.bedtimestory.entity.NewItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Deity on 2016/10/24.
 */

public class NewItemDaoImpl implements NewItemDao {

    public static NewItemDaoImpl instance = new NewItemDaoImpl();

    private NewItemDaoImpl(){}

    @Override
    public void addNewItemEntities(List<NewItem> newItems) {
        if (null!=newItems&&!newItems.isEmpty()){
            for (NewItem entity:newItems){
                NewItemEntity result = Realm.getDefaultInstance().where(NewItemEntity.class).equalTo("title",entity.getTitle()).findFirst();
                if (null==result){
                    addNewItemEntity(entity);
                }
            }
        }


    }

    @Override
    public void addNewItemEntity(NewItem newItem) {
        Realm.getDefaultInstance().beginTransaction();
        NewItemEntity entity = Realm.getDefaultInstance().createObject(NewItemEntity.class,System.currentTimeMillis());
        entity.setContent(newItem.getContent());
        entity.setDate(newItem.getDate());
        entity.setImgLink(newItem.getImgLink());
        entity.setLink(newItem.getLink());
        entity.setNewsType(newItem.getNewsType());
        entity.setTitle(newItem.getTitle());
        Realm.getDefaultInstance().commitTransaction();

    }

    /**需要在主线程上调用*/
    @Override
    public RealmResults<NewItemEntity> queryNewItemEntities(int newsType) {
        return Realm.getDefaultInstance().where(NewItemEntity.class).equalTo("newsType",newsType).findAllAsync();
    }
}
