package vux.codejava.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface ExcelExportServices {

	void exportShift(HttpServletResponse reponse) throws IOException;
}
