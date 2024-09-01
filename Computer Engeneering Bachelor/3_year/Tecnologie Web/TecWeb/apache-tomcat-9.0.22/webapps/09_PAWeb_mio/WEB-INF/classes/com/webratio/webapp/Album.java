package com.webratio.webapp;

public class Album implements java.io.Serializable {
    /** Serial version identifier. */
    private static final long serialVersionUID = 1L;

    private java.lang.Integer _aid;

    private java.lang.String _titolo;

    private java.lang.Integer _anno;

    private java.util.Set _albumToBrano = new java.util.HashSet();

    private float _searchScore;

    public java.lang.Integer getAid() {
        return _aid;
    }

    public void setAid(java.lang.Integer _aid) {
        this._aid = _aid;
    }

    public java.lang.String getTitolo() {
        return _titolo;
    }

    public void setTitolo(java.lang.String _titolo) {
        this._titolo = _titolo;
    }

    public java.lang.Integer getAnno() {
        return _anno;
    }

    public void setAnno(java.lang.Integer _anno) {
        this._anno = _anno;
    }

    public java.util.Set getAlbumToBrano() {
        return _albumToBrano;
    }

    public void setAlbumToBrano(java.util.Set _albumToBrano) {
        this._albumToBrano = _albumToBrano;
    }

    public float getSearchScore() {
        return _searchScore;
    }

    public void setSearchScore(float _searchScore) {
        this._searchScore = _searchScore;
    }

    public String toString() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer();
        sb.append(super.toString());
        int n = sb.length() - 1;
        if (sb.charAt(n) == ']') {
            sb.setCharAt(n, ',');
        } else {
            sb.append('[');
        }
        if (_aid != null)
            sb.append("aid=" + _aid + ',');
        if (_titolo != null)
            sb.append("titolo=" + _titolo + ',');
        if (_anno != null)
            sb.append("anno=" + _anno + ',');
        n = sb.length() - 1;
        if (sb.charAt(n) == ',') {
            sb.setCharAt(n, ']');
        } else if (sb.charAt(n) == '[') {
            sb.deleteCharAt(n);
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.webratio.webapp.Album)) {
            return false;
        }
        com.webratio.webapp.Album __other = (com.webratio.webapp.Album) obj;
        java.lang.Object key = null;
        java.lang.Object otherKey = null;
        key = this.getAid();
        otherKey = __other.getAid();
        if (key == null) {
            if (otherKey != null) {
                return false;
            }
        } else {
            if (otherKey == null) {
                return false;
            } else if (!key.equals(otherKey)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = 0;
        java.lang.Object key = null;
        key = this.getAid();
        if (key != null) {
            hashCode |= key.hashCode();
        }
        return hashCode;
    }
}
