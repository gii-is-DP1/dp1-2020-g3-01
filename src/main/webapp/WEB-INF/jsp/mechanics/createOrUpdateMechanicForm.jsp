<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="mechanics">
    <h2>
        <c:if test="${mechanic['new']}">New </c:if> Mechanic
    </h2>
    <form:form modelAttribute="mechanic" class="form-horizontal" id="add-mechanic-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="Birth Date" name="birthDate"/>
            <petclinic:inputField label="Residence" name="residence"/>
            <petclinic:inputField label="Nationality" name="nationality"/>
             <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:selectField label="Type" name="type" names="${types}" size="${types.size()}"/>
            <petclinic:inputField label="Username" name="user.username"/>
            <petclinic:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${mechanic['new']}">
                        <button class="btn btn-default" type="submit">Add Mechanic</button>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
