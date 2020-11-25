<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pilots">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        <c:if test="${pilot['new']}">New </c:if> Pilot
    </h2>
    <form:form modelAttribute="pilot" class="form-horizontal" id="add-pilot-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Birth Date" name="birthDate"/>
            <petclinic:inputField label="Residence" name="residence"/>
            <petclinic:inputField label="Nationality" name="nationality"/>
            <petclinic:inputField label="Number" name="number"/>
            <petclinic:inputField label="Height" name="height"/>
            <petclinic:inputField label="Weight" name="weight"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${pilot['new']}">
                        <button class="btn btn-default" type="submit">Add Pilot</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Pilot</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
