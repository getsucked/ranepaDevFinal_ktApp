package com.github.anrimian.musicplayer.domain.models.composition;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created on 24.10.2017.
 */

public class FullComposition {

    @Nullable
    private final String artist;
    @Nullable
    private final String title;
    @Nullable
    private final String album;
    @Nullable
    private final String albumArtist;
    @Nullable
    private final Long trackNumber;
    @Nullable
    private final Long discNumber;
    @Nullable
    private final String comment;
    @Nullable
    private final String lyrics;
    @Nonnull
    private final String fileName;

    private final long duration;
    private final long size;
    private final long id;

    @Nullable
    private final Long storageId;

    @Nonnull
    private final Date dateAdded;
    @Nonnull
    private final Date dateModified;
    @Nonnull
    private final Date coverModifyTime;

    @Nullable
    private final CorruptionType corruptionType;

    private final InitialSource initialSource;

    @SuppressWarnings("NullableProblems")
    public FullComposition(String artist,
                           String title,
                           String album,
                           String albumArtist,
                           @Nullable Long trackNumber,
                           @Nullable Long discNumber,
                           @Nullable String comment,
                           String lyrics,
                           String fileName,
                           long duration,
                           long size,
                           long id,
                           Long storageId,
                           Date dateAdded,
                           Date dateModified,
                           Date coverModifyTime,
                           CorruptionType corruptionType,
                           InitialSource initialSource) {
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.albumArtist = albumArtist;
        this.lyrics = lyrics;
        this.trackNumber = trackNumber;
        this.discNumber = discNumber;
        this.comment = comment;
        this.fileName = fileName;
        this.duration = duration;
        this.size = size;
        this.id = id;
        this.storageId = storageId;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.coverModifyTime = coverModifyTime;
        this.corruptionType = corruptionType;
        this.initialSource = initialSource;
    }

    @Nullable
    public String getAlbumArtist() {
        return albumArtist;
    }

    @Nullable
    public Long getStorageId() {
        return storageId;
    }

    @Nullable
    public String getArtist() {
        return artist;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getAlbum() {
        return album;
    }

    @Nonnull
    public String getFileName() {
        return fileName;
    }

    public long getDuration() {
        return duration;
    }

    public long getSize() {
        return size;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public Date getDateAdded() {
        return dateAdded;
    }

    @Nonnull
    public Date getDateModified() {
        return dateModified;
    }

    @Nonnull
    public Date getCoverModifyTime() {
        return coverModifyTime;
    }

    @Nullable
    public CorruptionType getCorruptionType() {
        return corruptionType;
    }

    @Nullable
    public Long getTrackNumber() {
        return trackNumber;
    }

    @Nullable
    public Long getDiscNumber() {
        return discNumber;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    @Nullable
    public String getLyrics() {
        return lyrics;
    }

    public InitialSource getInitialSource() {
        return initialSource;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "\n id=" + id +
                "\n fileName='" + fileName + '\'' +
                "\n duration=" + duration +
                "\n size=" + size +
                "\n dateAdded=" + dateAdded +
                "\n dateModified=" + dateModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullComposition that = (FullComposition) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
