<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="motorcycle">
    <h2>
        <c:if test="${motorcycle['new']}">New </c:if> motorcycle
    </h2>
    <form:form modelAttribute="motorcycle" class="form-horizontal" id="add-bike-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Brand" name="brand"/>
            <petclinic:inputField label="Displacement" name="displacement"/>
            <petclinic:inputField label="Horse Power" name="horsePower"/>
            <petclinic:inputField label="Weight" name="weight"/>
            <petclinic:inputField label="Tank Capacity" name="tankCapacity"/>
            <petclinic:inputField label="Max Speed" name="maxSpeed"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${motorcycle['new']}">
                        <button class="btn btn-default" type="submit">Add motorbike</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update motorbike</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
