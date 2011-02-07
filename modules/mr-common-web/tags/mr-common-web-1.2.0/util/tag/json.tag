<%@ tag body-content="scriptless"
%><%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><json:object>
	<jsp:doBody />
	<c:if test="${response.exceptions!=null}">
		<json:array name="exceptions" items="${response.exceptions}" />
	</c:if>
	<c:if test="${uuid!=null}">
		<json:property name="uuid" value="${response.uuid}" />
	</c:if>
	<json:property name="success" value="${response.success}" />
</json:object><% request.removeAttribute("javax.servlet.error.exception"); %>