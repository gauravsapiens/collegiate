package com.collegiate.database.impl;

import com.collegiate.bean.Category;
import com.collegiate.bean.College;
import com.collegiate.bean.Course;
import com.collegiate.database.CategoryDao;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.ColumnAlias;
import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.Collection;

/**
 * Created by gauravarora on 01/09/15.
 */
public class CategoryDaoImpl extends BaseDao<Category> implements CategoryDao {

    @Override
    public Collection<Category> getCategory(College college) {
        ColumnAlias columnAlias1 = ColumnAlias.columnWithTable("category", "id");
        ColumnAlias columnAlias2 = ColumnAlias.columnWithTable("category", "imageUrl");
        ColumnAlias columnAlias3 = ColumnAlias.columnWithTable("category", "name");

        Where<Category> where = new Select(columnAlias1, columnAlias2, columnAlias3)
                .distinct()
                .from(getBeanClass())
                .join(Course.class, Join.JoinType.LEFT)
                .on(Condition.column("category.id").eq("course.categoryId"))
                .where("collegeId=?", college.getId())
                .and(Condition.column("subscribed").eq(false))
                .orderBy("category.name");

        return where.queryList();
    }

    @Override
    protected Class<Category> getBeanClass() {
        return Category.class;
    }

}
