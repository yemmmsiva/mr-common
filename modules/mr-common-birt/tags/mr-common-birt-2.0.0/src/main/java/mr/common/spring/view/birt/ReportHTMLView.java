package mr.common.spring.view.birt;


/**
 * Clase para la generación de reportes en formato HTML.
 */
public class ReportHTMLView extends ReportAbstractView {

    public ReportHTMLView() {
        setContentType("text/html");
    }

    @Override
    public String getRenderOutputFormat() {
        return ReportAbstractView.OUTPUT_FORMAT_HTML;
    }

    @Override
    public String getViewName() {
        return "reportHTML";
    }
}
