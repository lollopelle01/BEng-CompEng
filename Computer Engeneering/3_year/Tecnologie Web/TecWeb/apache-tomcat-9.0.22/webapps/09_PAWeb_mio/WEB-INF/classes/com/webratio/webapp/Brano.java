package com.webratio.webapp;

public class Brano implements java.io.Serializable {
    /** Serial version identifier. */
    private static final long serialVersionUID = 1L;

    private java.lang.Integer _bid;

    private java.lang.String _titolo;

    private java.math.BigDecimal _durata;

    private com.webratio.webapp.Album _branoToAlbum;

    private float _searchScore;

    public java.lang.Integer getBid() {
        return _bid;
    }

    public void setBid(java.lang.Integer _bid) {
        this._bid = _bid;
    }

    public java.lang.String getTitolo() {
        return _titolo;
    }

    public void setTitolo(java.lang.String _titolo) {
        this._titolo = _titolo;
    }

    public java.math.BigDecimal getDurata() {
        return _durata;
    }

    public void setDurata(java.math.BigDecimal _durata) {
        this._durata = _durata;
    }

    public com.webratio.webapp.Album getBranoToAlbum() {
        return _branoToAlbum;
    }

    public void setBranoToAlbum(com.webratio.webapp.Album _branoToAlbum) {
        this._branoToAlbum = _branoToAlbum;
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
        if (_bid != null)
            sb.append("bid=" + _bid + ',');
        if (_titolo != null)
            sb.append("titolo=" + _titolo + ',');
        if (_durata != null)
            sb.append("durata=" + _durata + ',');
        n = sb.length() - 1;
        if (sb.charAt(n) == ',') {
            sb.setCharAt(n, ']');
        } else if (sb.charAt(n) == '[') {
            sb.deleteCharAt(n);
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.webratio.webapp.Brano)) {
            return false;
        }
        com.webratio.webapp.Brano __other = (com.webratio.webapp.Brano) obj;
        java.lang.Object key = null;
        java.lang.Object otherKey = null;
        key = this.getBid();
        otherKey = __other.getBid();
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
        key = this.getBid();
        if (key != null) {
            hashCode |= key.hashCode();
        }
        return hashCode;
    }
}
