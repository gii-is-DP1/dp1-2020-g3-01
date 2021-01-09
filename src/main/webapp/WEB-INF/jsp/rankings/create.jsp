<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<petclinic:layout pageName="position">

	<h2>List of Positions</h2>
	<form:form modelAttribute="positions" class="form-horizontal" id="add-position-form">	
		<c:forEach var="positions" items="${positions}">	
			<div class="form-group has-feedback">
				<c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    			<c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    
				<div class="${cssGroup}">
				    <label class="col-sm-2 control-label">${positions.pilot.firstName} ${positions.pilot.lastName}</label>
					<div class="col-sm-10">
			            <form:input class="form-control" path="${positions.pos}"/>
			            <c:if test="${valid}">
			                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
			            </c:if>
			            <c:if test="${status.error}">
			                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
			                <span class="help-inline">${status.errorMessage}</span>
			            </c:if>
			        </div>
				</div>
			</div>
		</c:forEach>
		<button class="btn btn-default" type="submit">Add Positions</button>
	</form:form>
</petclinic:layout>
