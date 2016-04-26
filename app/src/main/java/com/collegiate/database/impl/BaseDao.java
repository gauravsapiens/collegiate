package com.collegiate.database.impl;

import com.collegiate.database.Dao;
import com.collegiate.database.async.DbAsyncWrapper;
import com.collegiate.database.async.DbTransactionCallbacks;
import com.collegiate.util.StringUtils;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Collection;

/**
 * Created by gauravarora on 01/08/15.
 */
public abstract class BaseDao<T extends BaseModel> implements Dao<T> {

    @Override
    public T create(T object) {
        object.save();
        return object;
    }

    @Override
    public T update(T object) {
        object.update();
        return object;
    }

    @Override
    public T createOrUpdate(T object) {
        object.save();
        return object;
    }

    @Override
    public void delete(String objectId) {
        new Delete()
                .from(getBeanClass())
                .where(getIdFieldName() + "=?", objectId)
                .queryClose();
    }

    @Override
    public T findById(String objectId) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + "=?", objectId)
                .querySingle();
    }

    @Override
    public void findById(String objectId, DbTransactionCallbacks<T> callbacks) {
        new DbAsyncWrapper().executeGet(params -> findById(objectId), callbacks);
    }

    @Override
    public Collection<T> findByIds(Collection<String> id) {
        return new Select()
                .from(getBeanClass())
                .where(getIdFieldName() + " IN ", StringUtils.delimiter(id, ','))
                .queryList();
    }

    @Override
    public Collection<T> findAll() {
        return new Select()
                .all()
                .from(getBeanClass())
                .queryList();
    }

    @Override
    public int count() {
        return findAll().size();
    }

    protected String getIdFieldName() {
        return "id";
    }

    protected abstract Class<T> getBeanClass();

}
