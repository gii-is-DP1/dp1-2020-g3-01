<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<petclinic:layout pageName="grandPrix">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#dayOfRace").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
	<h2>
		<c:if test="${grandPrix['new']}">New </c:if>
		grandPrix
	</h2>
	<form:form modelAttribute="grandPrix" class="form-horizontal" id="add-grandPrix-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Location" name="location" />
			<petclinic:inputField label="Circuit" name="circuit" />
			<petclinic:inputField label="Laps" name="laps" />
			<petclinic:inputField label="Distance" name="distance" />
			<petclinic:inputField label="Day of race" name="dayOfRace" />

		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${grandPrix['new']}">
						<button class="btn btn-default" type="submit">Add
							grandPrix</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update
							grandPrix</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
	</jsp:body>
</petclinic:layout>