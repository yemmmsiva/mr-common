package mr.common.spring.view.birt;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Clase para la generación de reportes en formato PDF
 */
public class ReportPDFView extends ReportAbstractView {

    public ReportPDFView() {
        setContentType("application/pdf");
    }

    @SuppressWarnings("rawtypes")
	@Override
    protected void renderMergedOutputModel(Map map, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = "reporte-" + Calendar.getInstance().getTimeInMillis() + ".pdf";
        if (map != null && map.containsKey("REPORT_NAME")) {
            name = map.get("REPORT_NAME").toString();
        }
        resp.setHeader("Content-Disposition", "inline; filename=" + name);
        resp.setHeader("Expires", "0");
        resp.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
        resp.setHeader("Pragma", "public");

        resp.setDateHeader("Expires", 0); //prevents caching at the proxy
        resp.setHeader("Cache-Control", "max-age=0");

        super.renderMergedOutputModel(map, req, resp);
    }

    @Override
    public String getRenderOutputFormat() {
        return ReportAbstractView.OUTPUT_FORMAT_PDF;
    }

    @Override
    public String getViewName() {
        return "pdfreport";
    }

}
