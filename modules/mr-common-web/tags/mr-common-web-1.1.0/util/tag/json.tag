<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ tag body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:object>
	<jsp:doBody />
	<json:object name="fwk">
		<c:if test="${response.fwkExceptions!=null}">
			<json:array name="fwkExceptions" items="${response.fwkExceptions}" />
		</c:if>
		<c:if test="${uuid!=null}">
			<json:array name="uuid" items="${response.uuid}" />
		</c:if>
		<c:if test="${flowExecutionKey!=null}">
			<json:property name="flowExecutionKey" value="${response.flowExecutionKey}" />
		</c:if>
	</json:object>
	<json:property name="success" value="${response.success}" />
</json:object><%request.removeAttribute("javax.servlet.error.exception");%>