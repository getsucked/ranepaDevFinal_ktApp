package com.github.anrimian.musicplayer.data.database.dao.genre;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.github.anrimian.musicplayer.data.database.dao.compositions.CompositionsDao;
import com.github.anrimian.musicplayer.data.database.entities.IdPair;
import com.github.anrimian.musicplayer.data.database.entities.albums.AlbumEntity;
import com.github.anrimian.musicplayer.data.database.entities.artist.ArtistEntity;
import com.github.anrimian.musicplayer.data.database.entities.composition.CompositionEntity;
import com.github.anrimian.musicplayer.data.database.entities.genres.GenreEntity;
import com.github.anrimian.musicplayer.data.database.entities.genres.GenreEntryEntity;
import com.github.anrimian.musicplayer.data.storage.providers.genres.StorageGenreItem;
import com.github.anrimian.musicplayer.domain.models.composition.Composition;
import com.github.anrimian.musicplayer.domain.models.genres.Genre;
import com.github.anrimian.musicplayer.domain.models.genres.ShortGenre;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GenreDao {

    @Query("SELECT name FROM genres")
    List<String> selectAllGenreNames();

    @Query("SELECT storageId as id," +
            "(SELECT storageId FROM compositions WHERE id = audioId) as audioId " +
            "FROM genre_entries " +
            "WHERE genreId = :genreId")
    List<StorageGenreItem> selectAllAsStorageGenreItems(long genreId);

    @Insert
    void insertAll(List<GenreEntity> entities);

    @Insert
    long insert(GenreEntity entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGenreEntities(List<GenreEntryEntity> entities);

    @Insert
    void insertGenreEntry(GenreEntryEntity entity);

    @RawQuery(observedEntities = {
            GenreEntity.class,
            GenreEntryEntity.class,
            CompositionEntity.class
    })
    Observable<List<Genre>> getAllObservable(SupportSQLiteQuery query);

    @RawQuery(observedEntities = { ArtistEntity.class, CompositionEntity.class, AlbumEntity.class, GenreEntryEntity.class })
    Observable<List<Composition>> getCompositionsInGenreObservable(SimpleSQLiteQuery query);

    static String getCompositionsQuery(boolean useFileName) {
        return "SELECT " +
                CompositionsDao.getCompositionSelectionQuery(useFileName) +
                "FROM compositions " +
                "WHERE id IN (SELECT audioId FROM genre_entries WHERE genreId = :genreId)";
    }

    @Query("SELECT " +
            "id as id " +
            "FROM compositions " +
            "WHERE id IN (SELECT audioId FROM genre_entries WHERE genreId = :genreId)")
    Single<List<Long>> getAllCompositionsByGenre(long genreId);

    @Query("SELECT " +
            "id as dbId, " +
            "storageId as storageId " +
            "FROM genres WHERE genres.storageId IS NOT NULL")
    List<IdPair> getGenresIds();

    @Query("SELECT id as id," +
            "name as name, " +
            "(SELECT count() FROM genre_entries WHERE genreId = genres.id) as compositionsCount, " +
            "(SELECT sum(duration) FROM compositions WHERE compositions.id IN (SELECT audioId FROM genre_entries WHERE genreId = genres.id)) as totalDuration " +
            "FROM genres " +
            "WHERE id = :genreId LIMIT 1")
    Observable<List<Genre>> getGenreObservable(long genreId);

    @Query("SELECT id, name " +
            "FROM genres " +
            "WHERE id IN(SELECT genreId FROM genre_entries WHERE audioId = :compositionId)")
    Observable<List<ShortGenre>> getShortGenresInComposition(long compositionId);

    @Query("SELECT id FROM genres WHERE name = :name")
    Long findGenre(String name);

    @Query("SELECT genreId FROM genre_entries WHERE audioId = :compositionId")
    Long[] getGenresByCompositionId(long compositionId);

    @Query("DELETE FROM genres " +
            "WHERE id IN(:ids) AND (SELECT count() FROM genre_entries WHERE genreId IN(:ids)) = 0")
    void deleteEmptyGenre(Long[] ids);

    @Query("DELETE FROM genres " +
            "WHERE id = :id AND (SELECT count() FROM genre_entries WHERE genreId = :id) = 0")
    void deleteEmptyGenre(long id);

    @Query("DELETE FROM genres " +
            "WHERE (SELECT count() FROM genre_entries WHERE genreId = genres.id) = 0")
    void deleteEmptyGenres();

    @Query("DELETE FROM genre_entries WHERE audioId = :compositionId AND genreId IN(:genreIds)")
    void removeGenreEntry(long compositionId, Long[] genreIds);

    @Query("DELETE FROM genre_entries WHERE audioId = :compositionId AND genreId = :genreId")
    void removeGenreEntry(long compositionId, long genreId);

    @Query("SELECT name FROM genres")
    String[] getGenreNames();

    @Query("UPDATE genres SET name = :name WHERE id = :genreId")
    void updateGenreName(String name, long genreId);

    @Query("DELETE FROM genres WHERE id = :genreId")
    void deleteGenre(long genreId);

    @Query("SELECT name FROM genres WHERE id = :genreId")
    String getGenreName(long genreId);

    @Query("SELECT EXISTS(SELECT 1 FROM genres WHERE id = :id)")
    boolean isGenreExists(long id);

    @Query("UPDATE genre_entries SET genreId = :newGenreId WHERE genreId = :oldGenreId")
    void changeCompositionsGenre(long oldGenreId, long newGenreId);

    @Query("UPDATE compositions " +
            "SET dateModified = :dateModified " +
            "WHERE id IN (SELECT audioId FROM genre_entries WHERE genreId = :genreId)")
    void updateGenreCompositionsModifyTime(long genreId, Date dateModified);
}
