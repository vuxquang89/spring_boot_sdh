package vux.codejava.entity.shift;


public class ShiftRequest {

	private Integer action;
	private String noteReceive;
	private String note;
	private String district;
	
	private int metroMDRadio;
	private String noteActionmetroMD;
	
	private int hwHCMDNGRadio;
	private String noteActionhwHCMDNG;
	
	private int m1HCMDNGRadio;
	private String noteActionm1HCMDNG;
	
	private int m2HCMDNGRadio;
	private String noteActionm2HCMDNG;
	
	private int dsHCMDNGRadio;
	private String noteActiondsHCMDNG;
	
	private int htcsMetroMTRadio;
	private String noteActionhtcsMetroMT;
	
	private int htcsHCMDNGRadio;
	private String noteActionhtcsHCMDNG;
	
	private int mobifoneMTRadio;
	private String noteActionmobifoneMT;
	
	private int cskhMTRadio;
	private String noteActioncskhMT;
	
	//MB
	private int metroMTRadio;
	private String noteActionmetroMT;
	
	private int hwHCMCTORadio;
	private String noteActionhwHCMCTO;
	
	private int hwHNIDNGRadio;
	private String noteActionhwHNIDNG;
	
	private int m1HNIDNGRadio;
	private String noteActionm1HNIDNG;
	
	private int m2HNIDNGRadio;
	private String noteActionm2HNIDNG;
	
	private int dsHNIDNGRadio;
	private String noteActiondsHNIDNG;
	
	private int htcsMetroMBRadio;
	private String noteActionhtcsMetroMB;
	
	private int htcsHWHNIDNGRadio;
	private String noteActionhtcsHWHNIDNG;
	
	private int mobifoneMBRadio;
	private String noteActionmobifoneMB;
	
	private int cskhMBRadio;
	private String noteActioncskhMB;
	
	public ShiftRequest() {}
	
	public ShiftRequest(int metroMDRadio, String noteActionmetroMD, int hwHCMDNGRadio, String noteActionhwHCMDNG,
			int m1hcmdngRadio, String noteActionm1HCMDNG, int m2hcmdngRadio, String noteActionm2HCMDNG,
			int dsHCMDNGRadio, String noteActiondsHCMDNG, int htcsMetroMTRadio, String noteActionhtcsMetroMT,
			int htcsHCMDNGRadio, String noteActionhtcsHCMDNG, int mobifoneMTRadio, String noteActionmobifoneMT,
			int cskhMTRadio, String noteActioncskhMT) {
		
		this.metroMDRadio = metroMDRadio;
		this.noteActionmetroMD = noteActionmetroMD;
		this.hwHCMDNGRadio = hwHCMDNGRadio;
		this.noteActionhwHCMDNG = noteActionhwHCMDNG;
		m1HCMDNGRadio = m1hcmdngRadio;
		this.noteActionm1HCMDNG = noteActionm1HCMDNG;
		m2HCMDNGRadio = m2hcmdngRadio;
		this.noteActionm2HCMDNG = noteActionm2HCMDNG;
		this.dsHCMDNGRadio = dsHCMDNGRadio;
		this.noteActiondsHCMDNG = noteActiondsHCMDNG;
		this.htcsMetroMTRadio = htcsMetroMTRadio;
		this.noteActionhtcsMetroMT = noteActionhtcsMetroMT;
		this.htcsHCMDNGRadio = htcsHCMDNGRadio;
		this.noteActionhtcsHCMDNG = noteActionhtcsHCMDNG;
		this.mobifoneMTRadio = mobifoneMTRadio;
		this.noteActionmobifoneMT = noteActionmobifoneMT;
		this.cskhMTRadio = cskhMTRadio;
		this.noteActioncskhMT = noteActioncskhMT;
	}

	public ShiftRequest(int metroMTRadio, String noteActionmetroMT, int hwHCMCTORadio, String noteActionhwHCMCTO,
			int hwHNIDNGRadio, String noteActionhwHNIDNG, int m1hnidngRadio, String noteActionm1HNIDNG,
			int m2hnidngRadio, String noteActionm2HNIDNG, int dsHNIDNGRadio, String noteActiondsHNIDNG,
			int htcsMetroMBRadio, String noteActionhtcsMetroMB, int htcsHWHNIDNGRadio, String noteActionhtcsHWHNIDNG,
			int mobifoneMBRadio, String noteActionmobifoneMB, int cskhMBRadio, String noteActioncskhMB) {
		
		this.metroMTRadio = metroMTRadio;
		this.noteActionmetroMT = noteActionmetroMT;
		this.hwHCMCTORadio = hwHCMCTORadio;
		this.noteActionhwHCMCTO = noteActionhwHCMCTO;
		this.hwHNIDNGRadio = hwHNIDNGRadio;
		this.noteActionhwHNIDNG = noteActionhwHNIDNG;
		m1HNIDNGRadio = m1hnidngRadio;
		this.noteActionm1HNIDNG = noteActionm1HNIDNG;
		m2HNIDNGRadio = m2hnidngRadio;
		this.noteActionm2HNIDNG = noteActionm2HNIDNG;
		this.dsHNIDNGRadio = dsHNIDNGRadio;
		this.noteActiondsHNIDNG = noteActiondsHNIDNG;
		this.htcsMetroMBRadio = htcsMetroMBRadio;
		this.noteActionhtcsMetroMB = noteActionhtcsMetroMB;
		this.htcsHWHNIDNGRadio = htcsHWHNIDNGRadio;
		this.noteActionhtcsHWHNIDNG = noteActionhtcsHWHNIDNG;
		this.mobifoneMBRadio = mobifoneMBRadio;
		this.noteActionmobifoneMB = noteActionmobifoneMB;
		this.cskhMBRadio = cskhMBRadio;
		this.noteActioncskhMB = noteActioncskhMB;
	}

	public Integer getAction() {
		return action;
	}
	public void setAction(Integer action) {
		this.action = action;
	}
	public String getNoteReceive() {
		return noteReceive;
	}
	public void setNoteReceive(String noteReceive) {
		this.noteReceive = noteReceive;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public int getMetroMDRadio() {
		return metroMDRadio;
	}
	public void setMetroMDRadio(int metroMDRadio) {
		this.metroMDRadio = metroMDRadio;
	}
	public String getNoteActionmetroMD() {
		return noteActionmetroMD;
	}
	public void setNoteActionmetroMD(String noteActionmetroMD) {
		this.noteActionmetroMD = noteActionmetroMD;
	}
	public int getHwHCMDNGRadio() {
		return hwHCMDNGRadio;
	}
	public void setHwHCMDNGRadio(int hwHCMDNGRadio) {
		this.hwHCMDNGRadio = hwHCMDNGRadio;
	}
	public String getNoteActionhwHCMDNG() {
		return noteActionhwHCMDNG;
	}
	public void setNoteActionhwHCMDNG(String noteActionhwHCMDNG) {
		this.noteActionhwHCMDNG = noteActionhwHCMDNG;
	}
	public int getM1HCMDNGRadio() {
		return m1HCMDNGRadio;
	}
	public void setM1HCMDNGRadio(int m1hcmdngRadio) {
		m1HCMDNGRadio = m1hcmdngRadio;
	}
	public String getNoteActionm1HCMDNG() {
		return noteActionm1HCMDNG;
	}
	public void setNoteActionm1HCMDNG(String noteActionm1HCMDNG) {
		this.noteActionm1HCMDNG = noteActionm1HCMDNG;
	}
	public int getM2HCMDNGRadio() {
		return m2HCMDNGRadio;
	}
	public void setM2HCMDNGRadio(int m2hcmdngRadio) {
		m2HCMDNGRadio = m2hcmdngRadio;
	}
	public String getNoteActionm2HCMDNG() {
		return noteActionm2HCMDNG;
	}
	public void setNoteActionm2HCMDNG(String noteActionm2HCMDNG) {
		this.noteActionm2HCMDNG = noteActionm2HCMDNG;
	}
	public int getDsHCMDNGRadio() {
		return dsHCMDNGRadio;
	}
	public void setDsHCMDNGRadio(int dsHCMDNGRadio) {
		this.dsHCMDNGRadio = dsHCMDNGRadio;
	}
	public String getNoteActiondsHCMDNG() {
		return noteActiondsHCMDNG;
	}
	public void setNoteActiondsHCMDNG(String noteActiondsHCMDNG) {
		this.noteActiondsHCMDNG = noteActiondsHCMDNG;
	}
	public int getHtcsMetroMTRadio() {
		return htcsMetroMTRadio;
	}
	public void setHtcsMetroMTRadio(int htcsMetroMTRadio) {
		this.htcsMetroMTRadio = htcsMetroMTRadio;
	}
	public String getNoteActionhtcsMetroMT() {
		return noteActionhtcsMetroMT;
	}
	public void setNoteActionhtcsMetroMT(String noteActionhtcsMetroMT) {
		this.noteActionhtcsMetroMT = noteActionhtcsMetroMT;
	}
	public int getHtcsHCMDNGRadio() {
		return htcsHCMDNGRadio;
	}
	public void setHtcsHCMDNGRadio(int htcsHCMDNGRadio) {
		this.htcsHCMDNGRadio = htcsHCMDNGRadio;
	}
	public String getNoteActionhtcsHCMDNG() {
		return noteActionhtcsHCMDNG;
	}
	public void setNoteActionhtcsHCMDNG(String noteActionhtcsHCMDNG) {
		this.noteActionhtcsHCMDNG = noteActionhtcsHCMDNG;
	}
	public int getMobifoneMTRadio() {
		return mobifoneMTRadio;
	}
	public void setMobifoneMTRadio(int mobifoneMTRadio) {
		this.mobifoneMTRadio = mobifoneMTRadio;
	}
	public String getNoteActionmobifoneMT() {
		return noteActionmobifoneMT;
	}
	public void setNoteActionmobifoneMT(String noteActionmobifoneMT) {
		this.noteActionmobifoneMT = noteActionmobifoneMT;
	}
	public int getCskhMTRadio() {
		return cskhMTRadio;
	}
	public void setCskhMTRadio(int cskhMTRadio) {
		this.cskhMTRadio = cskhMTRadio;
	}
	public String getNoteActioncskhMT() {
		return noteActioncskhMT;
	}
	public void setNoteActioncskhMT(String noteActioncskhMT) {
		this.noteActioncskhMT = noteActioncskhMT;
	}
	public int getMetroMTRadio() {
		return metroMTRadio;
	}
	public void setMetroMTRadio(int metroMTRadio) {
		this.metroMTRadio = metroMTRadio;
	}
	public String getNoteActionmetroMT() {
		return noteActionmetroMT;
	}
	public void setNoteActionmetroMT(String noteActionmetroMT) {
		this.noteActionmetroMT = noteActionmetroMT;
	}
	public int getHwHCMCTORadio() {
		return hwHCMCTORadio;
	}
	public void setHwHCMCTORadio(int hwHCMCTORadio) {
		this.hwHCMCTORadio = hwHCMCTORadio;
	}
	public String getNoteActionhwHCMCTO() {
		return noteActionhwHCMCTO;
	}
	public void setNoteActionhwHCMCTO(String noteActionhwHCMCTO) {
		this.noteActionhwHCMCTO = noteActionhwHCMCTO;
	}
	public int getHwHNIDNGRadio() {
		return hwHNIDNGRadio;
	}
	public void setHwHNIDNGRadio(int hwHNIDNGRadio) {
		this.hwHNIDNGRadio = hwHNIDNGRadio;
	}
	public String getNoteActionhwHNIDNG() {
		return noteActionhwHNIDNG;
	}
	public void setNoteActionhwHNIDNG(String noteActionhwHNIDNG) {
		this.noteActionhwHNIDNG = noteActionhwHNIDNG;
	}
	public int getM1HNIDNGRadio() {
		return m1HNIDNGRadio;
	}
	public void setM1HNIDNGRadio(int m1hnidngRadio) {
		m1HNIDNGRadio = m1hnidngRadio;
	}
	public String getNoteActionm1HNIDNG() {
		return noteActionm1HNIDNG;
	}
	public void setNoteActionm1HNIDNG(String noteActionm1HNIDNG) {
		this.noteActionm1HNIDNG = noteActionm1HNIDNG;
	}
	public int getM2HNIDNGRadio() {
		return m2HNIDNGRadio;
	}
	public void setM2HNIDNGRadio(int m2hnidngRadio) {
		m2HNIDNGRadio = m2hnidngRadio;
	}
	public String getNoteActionm2HNIDNG() {
		return noteActionm2HNIDNG;
	}
	public void setNoteActionm2HNIDNG(String noteActionm2HNIDNG) {
		this.noteActionm2HNIDNG = noteActionm2HNIDNG;
	}
	public int getDsHNIDNGRadio() {
		return dsHNIDNGRadio;
	}
	public void setDsHNIDNGRadio(int dsHNIDNGRadio) {
		this.dsHNIDNGRadio = dsHNIDNGRadio;
	}
	public String getNoteActiondsHNIDNG() {
		return noteActiondsHNIDNG;
	}
	public void setNoteActiondsHNIDNG(String noteActiondsHNIDNG) {
		this.noteActiondsHNIDNG = noteActiondsHNIDNG;
	}
	public int getHtcsMetroMBRadio() {
		return htcsMetroMBRadio;
	}
	public void setHtcsMetroMBRadio(int htcsMetroMBRadio) {
		this.htcsMetroMBRadio = htcsMetroMBRadio;
	}
	public String getNoteActionhtcsMetroMB() {
		return noteActionhtcsMetroMB;
	}
	public void setNoteActionhtcsMetroMB(String noteActionhtcsMetroMB) {
		this.noteActionhtcsMetroMB = noteActionhtcsMetroMB;
	}
	public int getHtcsHWHNIDNGRadio() {
		return htcsHWHNIDNGRadio;
	}
	public void setHtcsHWHNIDNGRadio(int htcsHWHNIDNGRadio) {
		this.htcsHWHNIDNGRadio = htcsHWHNIDNGRadio;
	}
	public String getNoteActionhtcsHWHNIDNG() {
		return noteActionhtcsHWHNIDNG;
	}
	public void setNoteActionhtcsHWHNIDNG(String noteActionhtcsHWHNIDNG) {
		this.noteActionhtcsHWHNIDNG = noteActionhtcsHWHNIDNG;
	}
	public int getMobifoneMBRadio() {
		return mobifoneMBRadio;
	}
	public void setMobifoneMBRadio(int mobifoneMBRadio) {
		this.mobifoneMBRadio = mobifoneMBRadio;
	}
	public String getNoteActionmobifoneMB() {
		return noteActionmobifoneMB;
	}
	public void setNoteActionmobifoneMB(String noteActionmobifoneMB) {
		this.noteActionmobifoneMB = noteActionmobifoneMB;
	}
	public int getCskhMBRadio() {
		return cskhMBRadio;
	}
	public void setCskhMBRadio(int cskhMBRadio) {
		this.cskhMBRadio = cskhMBRadio;
	}
	public String getNoteActioncskhMB() {
		return noteActioncskhMB;
	}
	public void setNoteActioncskhMB(String noteActioncskhMB) {
		this.noteActioncskhMB = noteActioncskhMB;
	}
	
	public DetailValue getValues(String code){
		DetailValue detailValue = new DetailValue();
		switch (code) {
		case "metroMD":
			detailValue.setAction(this.metroMDRadio);
			detailValue.setNoteAction(this.noteActionmetroMD);
			break;
		case "hwHCMDNG":
			detailValue.setAction(this.hwHCMDNGRadio);
			detailValue.setNoteAction(this.noteActionhwHCMDNG);
			break;
		case "m1HCMDNG":
			detailValue.setAction(this.m1HCMDNGRadio);
			detailValue.setNoteAction(this.noteActionm1HCMDNG);
			break;
		case "m2HCMDNG":
			detailValue.setAction(this.m2HCMDNGRadio);
			detailValue.setNoteAction(this.noteActionm2HCMDNG);
			break;
		case "dsHCMDNG":
			detailValue.setAction(this.dsHCMDNGRadio);
			detailValue.setNoteAction(this.noteActiondsHCMDNG);
			break;
		case "htcsMetroMT":
			detailValue.setAction(this.htcsMetroMTRadio);
			detailValue.setNoteAction(this.noteActionhtcsMetroMT);
			break;
		case "htcsHCMDNG":
			detailValue.setAction(this.htcsHCMDNGRadio);
			detailValue.setNoteAction(this.noteActionhtcsHCMDNG);
			break;
		case "mobifoneMT":
			detailValue.setAction(this.mobifoneMTRadio);
			detailValue.setNoteAction(this.noteActionmobifoneMT);
			break;
		case "cskhMT":
			detailValue.setAction(this.cskhMTRadio);
			detailValue.setNoteAction(this.noteActioncskhMT);
			break;
			
		case "metroMT":
			detailValue.setAction(this.metroMTRadio);
			detailValue.setNoteAction(this.noteActionmetroMT);
			break;
		case "hwHCMCTO":
			detailValue.setAction(this.hwHCMCTORadio);
			detailValue.setNoteAction(this.noteActionhwHCMCTO);
			break;
		case "hwHNIDNG":
			detailValue.setAction(this.hwHNIDNGRadio);
			detailValue.setNoteAction(this.noteActionhwHNIDNG);
			break;
		case "m1HNIDNG":
			detailValue.setAction(this.m1HNIDNGRadio);
			detailValue.setNoteAction(this.noteActionm1HNIDNG);
			break;
		case "m2HNIDNG":
			detailValue.setAction(this.m2HNIDNGRadio);
			detailValue.setNoteAction(this.noteActionm2HNIDNG);
			break;
		case "dsHNIDNG":
			detailValue.setAction(this.dsHNIDNGRadio);
			detailValue.setNoteAction(this.noteActiondsHNIDNG);
			break;
		case "htcsMetroMB":
			detailValue.setAction(this.htcsMetroMBRadio);
			detailValue.setNoteAction(this.noteActionhtcsMetroMB);
			break;
		case "htcsHWHNIDNG":
			detailValue.setAction(this.htcsHWHNIDNGRadio);
			detailValue.setNoteAction(this.noteActionhtcsHWHNIDNG);
			break;
		case "mobifoneMB":
			detailValue.setAction(this.mobifoneMBRadio);
			detailValue.setNoteAction(this.noteActionmobifoneMB);
			break;
		case "cskhMB":
			detailValue.setAction(this.cskhMBRadio);
			detailValue.setNoteAction(this.noteActioncskhMB);
			break;

		default:
			break;
		}
		
		return detailValue;
	}
	
}
