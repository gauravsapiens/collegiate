package com.collegiate.database.impl;

import com.collegiate.bean.Lecture;
import com.collegiate.database.LectureDao;
import com.collegiate.database.async.DbAsyncWrapper;
import com.collegiate.database.async.DbTransactionCallbacks;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Update;

import java.util.Collection;


/**
 * Created by gauravarora on 01/08/15.
 */
public class LectureDaoImpl extends BaseDao<Lecture> implements LectureDao {

    @Override
    public Collection<Lecture> getLecturesForCourse(String courseId) {
        return new Select()
                .from(getBeanClass())
                .where("courseId =?", courseId)
                .queryList();
    }

    @Override
    public void getLecturesForCourse(String courseId, DbTransactionCallbacks<Collection<Lecture>> callbacks) {
        if(courseId == null || callbacks == null){
            return;
        }

        new DbAsyncWrapper().executeGet(params -> getLecturesForCourse(courseId), callbacks);
    }

    @Override
    public void markAsSeen(String lectureId, DbTransactionCallbacks<Void> callbacks) {
        new DbAsyncWrapper().executeUpdate(params -> new Update<>(getBeanClass())
                .set("seen=?", 1)
                .where("id=?", lectureId)
                .queryClose(), callbacks);
    }

    @Override
    public void deleteLecturesForCourse(String courseId) {
        new Delete()
                .from(getBeanClass())
                .where("courseId=?", courseId)
                .queryClose();
    }

    @Override
    public void updateBookmark(String lectureId, int bookmark) {
        new Update<>(getBeanClass())
                .set("bookmark=?", bookmark)
                .where("id=?", lectureId)
                .queryClose();
    }

    @Override
    protected Class<Lecture> getBeanClass() {
        return Lecture.class;
    }
}
