================================
1#	Telechager et Installer Github
===============================

	lien windows http://windows.github.com/
	
================================================
2#	Crée un compte sur github 
================================================

	1# aller sur https://www.github.com
	2# crée un compte 
	
===================================
3#  le projet 
===================================

	voici le lien du depot du projet:
	il comporte 4 branche, chacun de nous travaillera sur une branche
	on évitera de travailler sur le même fichier afin de faciliter la gestion 
	des confusions

==============================	
4#	Forker le projet 
==============================

	connecter vous sur votre compte Github
	Pour créer un fork, allez sur le projet que vous voulez forker, donc notre projet https://github.com/SupGalileeGroupe4/CahierDeSonNumerique
	et dans le menu du haut cliquez sur le bouton Fork :
	
	vous aurais le projet dans votre compte github
	
===============================
5# Utiliser le projet forker 
================================
	
	Mise en place de la version locale
	Il suffit d’exploiter le fork comme un dépot normal. Tout d’abord, clônez le
	Taper les commande suivante:
	votre_nom_utilisateur: est votre login qui vous permet de vous connecté sur github
	
	git clone git@github.com:votre_nom_utilisateur/CahierDeSonNumerique.git
	cd domogik
	
	=======
	
	Ajoutez un alias nommé upstream vers le dépôt original (que vous avez forké "projet original"),
	Ceci permettra de récupérer ses évolutions et aussi de proposer votre tache ou partie réaliser la dessus.
	Taper la commande suivante:
	
	git remote add upstream https://github.com/SupGalileeGroupe4/CahierDeSonNumerique.git
	
	===============
	
	Mise à jour du dépôt local forké depuis le dépôt d’origine
	Il suffit de faire un fetch sur l’alias du dépôt d’origine :
	Commande:
	
	git fetch upstream
	
	Vous aurez maintenant accès aux branches du dépots Original du projet.
	(Vous aurez accès au travail ou partir réaliser par les autres membres du projet )
	
===========================================
6#	Réaliser votre tâche sur une branche
===========================================

	Tout d’abord, créez une branche dédiée et passez dessus :
	Commande:

	git branch <le_nom_explicite_de_ma_branche_ou_nom_de_la_tache>
	git checkout <le_nom_explicite_de_ma_branche_ou_nom_de_la_tache>
	
	============================================
	
	Vous travailler sur un fichier ou 2 fichiers ou plus ....
	
	Ajouter le fichier a votre branche afin que git puisse le suivre
	Commande:
				git add <nom_du_fichier>
				
	Vous avez fait des modifications sur un fichier 
	vous voulez que git tienne compte de cette modification dans votre depot LOCAL.
	Commande:
				git commit -m 'message decrivant la modification' <nom_du_fichier_ayant_été_modifier>
				
	
	vous voulez que git tienne compte de cette modification dans votre depot DISTANT sur le serveur web de github.
	Commande:		
				git push origin <le_nom_explicite_de_ma_branche_ou_nom_de_la_tache>
	

=================================================================
7#	Integartion de votre tâche au Projet
=================================================================

	Pour soumettre votre tache ou mission réalisé dépôt d’origine, 
	il faut faire une pull request. Pour ceci, allez sur la page de votre fork sur github 
	et commencez par sélectionner la branche sur laquelle vous avez travailler.
	Puis cliquez sur le bouton Pull request :
	Vous accédez à un formulaire avec plusieurs parties :
	En dessous il y a 3 onglets. Le premier est à remplir avec un titre pour votre pull request 
	et une description de ce que vous avez fait. Le second liste les commits que vous proposez 
	et le 3ème affiche les modifications sur les fichiers.
	 
	exemple:
	Titre: donner le titre de votre tâche 
	Write: Décrire en quelque mots les traveaux réalisés sur cette tâche
	
	Une fois tout renseigné et surtout vérifié, vous pouvez cliquer sur le bouton Send pull request.
	
	

	***********************************************************************************************
		Après un test par un autre membre de l'équipe votre tâche réaliser sera intégré au projet
	***********************************************************************************************
	Commande:
				git checkout master
				git merge <le_nom_explicite_de_ma_branche_ou_nom_de_la_tache>

	
	
		
	
