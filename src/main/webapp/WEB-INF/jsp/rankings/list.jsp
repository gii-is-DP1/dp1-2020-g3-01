<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<petclinic:layout pageName="team">

	<h2>List of Position of grand prix of ${grandprix.location}</h2>
	<table id="tournamentesTable" class="table table-striped">
		<thead>
			<tr>
				<th>Pilot</th>
				<th>Position</th>
				<th>Points</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${positions}" var="position">
				<tr>
					
					<td><c:out value="${position.pilot.firstName} ${position.pilot.lastName}" /></td>
					<td><c:out value="${position.pos}" /></td>
					<td><c:out value="${position.point}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>


</petclinic:layout>
