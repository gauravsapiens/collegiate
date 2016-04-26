package com.collegiate.database;

import com.collegiate.bean.Notes;

/**
 * Created by gauravarora on 25/09/15.
 */
public interface NotesDao extends Dao<Notes>{

    Notes getNotesForLecture(String lectureId);

}
