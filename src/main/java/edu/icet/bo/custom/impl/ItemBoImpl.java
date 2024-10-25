package edu.icet.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.bo.custom.ItemBo;
import edu.icet.dao.DaoFactory;
import edu.icet.dao.custom.ItemDao;
import edu.icet.entity.ItemEntity;
import edu.icet.entity.SupplierEntity;
import edu.icet.model.Item;
import edu.icet.model.Supplier;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemBoImpl implements ItemBo {

    ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public String generateItemId() {
        String lastItemId = itemDao.getLatestId();
        if (lastItemId==null){
            return "I0001";
        }
        int number = Integer.parseInt(lastItemId.split("I")[1]);
        number++;
        return String.format("I%04d", number);
    }

    @Override
    public boolean insertItem(Item item) {
        ItemEntity itemEntity = new ObjectMapper().convertValue(item, ItemEntity.class);
        return itemDao.save(itemEntity);
    }

    @Override
    public ObservableList getAllUsers() {
        ObservableList<ItemEntity> list = itemDao.getAll();
        ObservableList<Item> itemList = FXCollections.observableArrayList();

        list.forEach(itemEntity -> {
            itemList.add(new ObjectMapper().convertValue(itemEntity,Item.class));
        });
        return itemList;
    }

    @Override
    public boolean updateItem(Item item) {
        ItemEntity itemEntity = new ObjectMapper().convertValue(item, ItemEntity.class);
        return itemDao.update(itemEntity);
    }

    @Override
    public boolean deleteItemById(String text) {
        return itemDao.delete(text);
    }

    @Override
    public Item searchItemByName(String name) {
        ItemEntity itemEntity = itemDao.searchByName(name);
        return new ObjectMapper().convertValue(itemEntity,Item.class);
    }

    @Override
    public Item searchItemByID(String id) {
        ItemEntity itemEntity = itemDao.search(id);
        return new ObjectMapper().convertValue(itemEntity,Item.class);
    }
}
