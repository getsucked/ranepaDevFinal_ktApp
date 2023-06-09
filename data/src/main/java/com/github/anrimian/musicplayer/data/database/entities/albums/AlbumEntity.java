package com.github.anrimian.musicplayer.data.database.entities.albums;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.github.anrimian.musicplayer.data.database.entities.artist.ArtistEntity;

import javax.annotation.Nullable;

@Entity(tableName = "albums",
        foreignKeys = {
                @ForeignKey(entity = ArtistEntity.class,
                        parentColumns = {"id"},
                        childColumns = {"artistId"})
        },
        indices = {
                @Index("artistId"),
                @Index(value = {"artistId", "name"}, unique = true)
        }
)
public class AlbumEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @Nullable
    private Long artistId;

    private String name;

    public AlbumEntity(@Nullable Long artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
