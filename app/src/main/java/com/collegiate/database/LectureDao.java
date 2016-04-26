package com.collegiate.database;

import com.collegiate.bean.Lecture;
import com.collegiate.database.async.DbTransactionCallbacks;

import java.util.Collection;

/**
 * Created by gauravarora on 01/08/15.
 */
public interface LectureDao extends Dao<Lecture> {

    Collection<Lecture> getLecturesForCourse(String courseId);

    void getLecturesForCourse(String courseId, DbTransactionCallbacks<Collection<Lecture>> callbacks);

    void markAsSeen(String lectureId, DbTransactionCallbacks<Void> callbacks);

    void deleteLecturesForCourse(String courseId);

    void updateBookmark(String lectureId, int bookmark);

}
