package com.tapcomiccomicreader.dto;

import com.tapcomiccomicreader.entity.ReadHistory;

public class HistoryDTO {
    private ComicDTO comicDTO;
    private ReadHistory historyDTO;

    public HistoryDTO(ReadHistory history) {
        historyDTO = history;
        comicDTO = new ComicDTO(history.getComic());
    }

    public ComicDTO getComicDTO() {
        return comicDTO;
    }

    public void setComicDTO(ComicDTO comicDTO) {
        this.comicDTO = comicDTO;
    }

    public ReadHistory getHistoryDTO() {
        return historyDTO;
    }

    public void setHistoryDTO(ReadHistory historyDTO) {
        this.historyDTO = historyDTO;
    }

    @Override
    public String toString() {
        return "HistoryDTO{" +
                "comicDTO=" + comicDTO +
                ", historyDTO=" + historyDTO +
                '}';
    }
}
