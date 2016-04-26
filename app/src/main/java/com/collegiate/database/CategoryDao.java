package com.collegiate.database;

import com.collegiate.bean.Category;
import com.collegiate.bean.College;

import java.util.Collection;

/**
 * Created by gauravarora on 01/09/15.
 */
public interface CategoryDao extends Dao<Category> {

    Collection<Category> getCategory(College college);
}
