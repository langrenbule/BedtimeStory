package com.deity.bedtimestory.dao;

import com.deity.bedtimestory.entity.NewItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.deity.bedtimestory.data.Params.SIZE_PER_PAGE;


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
                    System.out.println("entity>>. type"+entity.getNewsType());
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
        entity.setNewsType(newItem.getNewsType());//Params.NewType.whichOne(
        entity.setTitle(newItem.getTitle());
        Realm.getDefaultInstance().commitTransaction();

    }

    /**需要在主线程上调用*/
    @Override
    public List<NewItemEntity> queryNewItemEntities(int newsType,int currentPage) {
        RealmResults<NewItemEntity> queryResult = Realm.getDefaultInstance().where(NewItemEntity.class).equalTo("newsType",newsType).findAll();
        int offset = SIZE_PER_PAGE*currentPage;
        //1.如果总数量大于偏移量并且大于Params.SIZE_PER_PAGE*currentPage+ Params.SIZE_PER_PAGE
        if (queryResult.size()>= offset){
            int lastIndex = (queryResult.size()-1)<=(SIZE_PER_PAGE*(1+currentPage))?(queryResult.size()-1):(SIZE_PER_PAGE*(1+currentPage));
            if (lastIndex<offset) lastIndex = offset;
            return queryResult.subList(offset,lastIndex);
        }
        return null;
    }
}
