<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<petclinic:layout pageName="position">

	<c:if test="${alert==true}">
		<script>
			alert('Positions have to be unique')
		</script>
	</c:if>

	<h2>List of Positions</h2>
	<form:form modelAttribute="grandprix" class="form-horizontal"
		id="add-position-form">

		<input type="hidden" name="location" value="${grandprix.location}" />
		<input type="hidden" name="circuit" value="${grandprix.circuit}" />
		<input type="hidden" name="laps" value="${grandprix.laps}" />
		<input type="hidden" name="distance" value="${grandprix.distance}" />
		<!-- 	<input type="hidden" name="positions" value="${grandprix.positions}"/>
	 	
	 	 <input type="hidden" name="dayOfRace" value="${grandprix.dayOfRace}"/>
	 	<input type="hidden" name="pilots" value="${grandprix.pilots}"/>
	 	<input type="hidden" name="team" value="${grandprix.team}"/> -->

		<input type="hidden" name="id" value="${grandprix.id}" />

		<c:forEach var="positions" items="${grandprix.positions}">

			<div class="form-group has-feedback">
				<c:set var="cssGroup"
					value="form-group ${status.error ? 'has-error' : '' }" />
				<c:set var="valid"
					value="${not status.error and not empty status.actualValue}" />

				<div class="${cssGroup}">
					<label class="col-sm-2 control-label">${positions.pilot.firstName}
						${positions.pilot.lastName}</label>
					<div class="col-sm-10">
						<input class="form-control" name="${positions.pilot.id}">
						<c:if test="${valid}">
							<span class="glyphicon glyphicon-ok form-control-feedback"
								aria-hidden="true"></span>
						</c:if>
						<c:if test="${status.error}">
							<span class="glyphicon glyphicon-remove form-control-feedback"
								aria-hidden="true"></span>
							<span class="help-inline">${status.errorMessage}</span>
						</c:if>
					</div>
				</div>
			</div>
		</c:forEach>
		<button class="btn btn-default" type="submit">Add Positions</button>
	</form:form>

</petclinic:layout>
