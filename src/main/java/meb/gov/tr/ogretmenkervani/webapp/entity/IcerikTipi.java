package meb.gov.tr.ogretmenkervani.webapp.entity;

public enum IcerikTipi {
    FOTOGRAF_SANATI("Fotoğraf Sanatı"),
    SIIR("Şiir"),
    EGITIMDE_IYI_ORNEKLER("Eğitimde İyi Örnekler"),
    OGRETMEN_ANILARI("Öğretmen Anıları"),
    AYIN_TEMASI("Ayın Teması"),
    IL_ILCE_TANITIMI("İl ya da İlçe Tanıtımı"),
    BASARI_HIKAYELERI("Başarı Hikâyeleri"),
    MEVZUAT_YONETMELIK("Mevzuat / Yönetmelik"),
    YOUTUBE_VIDEO("video içerikleri");
    private final String displayName;

    IcerikTipi(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
