package com.collegiate.database.impl;

import com.collegiate.bean.Notes;
import com.collegiate.database.NotesDao;

/**
 * Created by gauravarora on 25/09/15.
 */
public class NotesDaoImpl extends BaseDao<Notes> implements NotesDao{

    @Override
    public Notes getNotesForLecture(String lectureId) {
        return findById(lectureId);
    }

    @Override
    protected String getIdFieldName() {
        return "lectureId";
    }

    @Override
    protected Class<Notes> getBeanClass() {
        return Notes.class;
    }

}
