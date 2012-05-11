-- Script : Cue
---- Système de files simple


cue = {}

function cue.new()
	local self = {}

	function self:push(data)
		self[#self+1]=data
		----fluxus:push("pushed "..data)
	end

	function self:getTruthTable()
		local tab = {}
		for i=0,77 do
			tab[i]=false
			----fluxus:push("init de la case "..i)
		end
		for i=1,#self do
			----fluxus:push("J'ai le "..tarot.utile.getNom(i))
			tab[self[i]]=true
		end
		return tab
	end
			
	
	function self:pop(index)
		index = index or 1
		if #self >= index then
			local data = self[index]
			table.remove(self, index)
			return data
		end
	end

	function self:isEmpty()
		return #self == 0
	end

	function self:clear()
		while not self:isEmpty() do
			self:pop()
		end
	end

	return self
end



-- Script : Gestion du flux de sortie
---- Crée un tableau de chaines de charatères et des fonctions associées


fluxus = cue.new()
	
--fluxus:push('Fluxus initialisé')			



-- Script : Initialisation du namespace 'tarot'
---- Crée une table 'tarot' qui sera utilisée par les différentes fonctions de l'IA


tarot={}
tarot.main=cue.new()
tarot.legal=cue.new()
tarot.pli=cue.new()
tarot.utile = {}
--fluxus:push('Namespaces créés')

function tarot.utile.getCouleur(carte)
	if carte < 0 or carte >=78 then
		return "erreur"
	elseif carte < 22 then
		return "atout"
	elseif carte < 36 then
		return "coeur"
	elseif carte < 50 then
		return "pique"
	elseif carte < 64 then
		return "carreau"
	else
		return "trefle"
	end
end

function tarot.utile.possede(tab, carte, ...)
	if carte==nil then
		return false
	else
		for i,v in ipairs(tab) do
			if carte == v then
				return carte
			end
		end
		return tarot.utile.possede(tab, ...)
	end
end

function tarot.utile.getValeur(carte)
	local couleur = tarot.utile.getCouleur(carte)
	if couleur == "atout" then
		return carte
	else
		local decalage = 0
		if couleur == "coeur" then
			decalage = 21
		elseif couleur == "pique" then
			decalage = 35
		elseif couleur == "carreau" then
			decalage = 49
		else
			decalage = 63
		end
		return carte - decalage
	end
end

function tarot.utile.newCarte(couleur, valeur)
	local couleur = couleur or "atout"
	local valeur = valeur or 1
	----fluxus:push("Nouvelle carte de couleur "..couleur.." et valeur "..valeur)
	if couleur == "atout" then
		----fluxus:push("Soit l'UID "..valeur)
		return valeur
	else
		local decalage = 0
		if couleur == "coeur" then
			decalage = 21
		elseif couleur == "pique" then
			decalage = 35
		elseif couleur == "carreau" then
			decalage = 49
		else
			decalage = 63
		end
		----fluxus:push("Soit l'UID "..(decalage+valeur))
		return decalage + valeur
	end
end

function tarot.utile.getNom(carte)
	if tarot.utile.getCouleur(carte)=="atout" then
		if carte == 0 then
			return "Excuse"
		elseif carte == 1 then
			return "Petit"
		else
			return carte.." d'atout"
		end
	else
		if tarot.utile.getValeur(carte)<11 then
			return tarot.utile.getValeur(carte).." de "..tarot.utile.getCouleur(carte)
		else
			if tarot.utile.getValeur(carte)==11 then
				return "Valet de "..tarot.utile.getCouleur(carte)
			elseif tarot.utile.getValeur(carte)==12 then
				return "Cavalier de "..tarot.utile.getCouleur(carte)
			elseif tarot.utile.getValeur(carte)==13 then
				return "Dame de "..tarot.utile.getCouleur(carte)
			else
				return "Roi de "..tarot.utile.getCouleur(carte)
			end
		end
	end
end

function tarot.utile.cartesACouleur(main, couleur)
	c = 0
	if couleur == "atout" then
		for i=0,21 do
			if tarot.utile.possede(main, i) then
				c = c+1
			end
		end
	else
		for i=1,14 do
			if tarot.utile.possede(main, tarot.utile.newCarte(couleur,i)) then
				c = c+1
			end
		end
	end
	return c
end

function tarot.utile.plusPetitACouleur(main, couleur)
	if couleur == "atout" then
		for i=0,21 do
			if tarot.utile.possede(main, i) then
				return i
			end
		end
	else
		for i=1,14 do
			if tarot.utile.possede(main, tarot.utile.newCarte(couleur,i)) then
				return tarot.utile.newCarte(couleur, i)
			end
		end
	end
end

function tarot.utile.plusGrandACouleur(main, couleur)
	if couleur == "atout" then
		for i=0,21 do
			if tarot.utile.possede(main, 21-i) then
				return 21-i
			end
		end
	else
		for i=1,14 do
			if tarot.utile.possede(main, tarot.utile.newCarte(couleur,15-i)) then
				return tarot.utile.newCarte(couleur, 15-i)
			end
		end
	end
end

-- Script : Carte aléatoire
---- 


function tarot.rndCard()
	return math.random(0,77)
end
flal = 5



-- Script : Tables standard
---- Utilisés par l'IA par défaut et utilisables par des IA customisés


tarot.chien=cue.new()
tarot.joueur={}
for i=1,4 do
	tarot.joueur[i]={}
	tarot.joueur[i].plis=cue.new()
	tarot.joueur[i].cartes={}
	tarot.joueur[i].cartes.possible={}
	tarot.joueur[i].cartes.probable={}
	for c=0,77 do
		tarot.joueur[i].cartes.possible[i]=true
		tarot.joueur[i].cartes.probable[i]=50
	end
end					

-- Script : Operations Callback
---- Fonctions à redéfinir pour créer une IA


tarot.demander = {}
tarot.dire = {}
	
function tarot.demander.annonce()
	return math.random(0,4)
end
	
function tarot.demander.ecart()
	local ecart = cue.new()
	for i=1,6 do
		ecart:push(tarot.main:pop(math.random(1,#tarot.main)))
	end
	return ecart
end

lastplayed = 100

function tarot.demander.carte()
	--fluxus:push("J'ai "..#tarot.main.." cartes dans la main, dont "..#tarot.legal.." légales")
	--c = tarot.legal:pop(math.random(1,#tarot.legal))
	if #tarot.legal == 0 then
		--fluxus:push("OMFG, I'm illegal!")
		c = math.random(0,77)
	else
		c = tarot.legal:pop(math.random(1,#tarot.legal))
		--fluxus:push("Je veux jouer: "..tarot.utile.getNom(c))
	end
	if lastplayed == c then
		--fluxus:push("J'ai déjà joué "..tarot.utile.getNom(c).." a la place je joue le 21")
		c = 21
		lastplayed = 21
	else
		lastplayed = c
	end
	return c
end
	
function tarot.dire.chien(chien)
end
	
function tarot.dire.cartejouee(carte, joueur)
end
	
function tarot.dire.annonce(annonce, joueur)
end
	
function tarot.dire.pliRemporte(pli, joueur)
end
	
function tarot.dire.main(main)
end
	
function tarot.dire.score(score)
end


-- Script : Script de test Lua
---- Script sans réelle utilité, ne sert que pour vérifier que Lua est chargé.


--fluxus:push('Script de test chargé')



-- Script : annonces
----  Permet de renvoyer l'annonce que veux faire l'IA (indexer 0 à 4)


flal = 1
function tarot.demander.annonce()
	local c = 0	
	local j = 21
	local tempC = 0
	nbrCarteParCouleur = {}
	nbrAtout = 0
	nbrAtoutMajeur =0
	
	maMain = tarot.main:getTruthTable()
	
	for i=0,21 do
		if maMain[i] then 
			nbrAtout = nbrAtout + 1
		end
	end
	
	for i=16,21 do
		if maMain[i] then
			nbrAtoutMajeur = nbrAtoutMajeur + 1
		end
	end

	c=(c+(nbrAtoutMajeur*2))

	for i=17,21 do
		if maMain[i-1] and maMain[i] then
			c=(c+1)
		end
	end
				
	if maMain[21] then
		c=(c+10)
	end
	if maMain[0] then
		c=(c+8)
	end
	
	if maMain[1] then
		if 5 > nbrAtout then
		end
		if nbrAtout == 4 then
			c=(c+5)
		end
		if nbrAtout == 5 then
			c=(c+7)
		end
		if nbrAtout >= 6 then
			c=(c+9)
		end
			
		if nbrAtout > 4 then
			c=(c+(nbrAtout*2))
		end				
	end
	
	for i=1,4 do
		nbrCarteParCouleur[i] = 0
		if maMain[j+14] and maMain[j + 13] then 
			c=(c+1)
		end
		if maMain[j+14] then 
			c=(c+6)
		end
		if maMain[j + 13] then 
			c=(c+3)
		end
		if maMain[j + 12] then 
			c=(c+2)
		end
		if maMain[j + 11] then 
			c=(c+1)
		end
		
		for k=1,14 do
			if maMain[j+k] then 
				nbrCarteParCouleur[i] = nbrCarteParCouleur[i]+1
			end
		end
		
		if nbrCarteParCouleur[i] == 0 then 
			c=(c+6)
		elseif nbrCarteParCouleur[i] == 1 then 
			c=(c+3)
		elseif nbrCarteParCouleur[i] == 5 then 
			c=(c+5)
		elseif nbrCarteParCouleur[i] == 6 then 
			c=(c+7)
		elseif nbrCarteParCouleur[i] >= 7 then 
			c=(c+9)
		end
		
		j = j + 14
	end
	flal=c
	--fluxus:push("Mon flal est "..flal)
	if c > 80 then 
		--fluxus:push("Je garde contre")
		return 4, flal
	elseif c > 70 then
		--fluxus:push("Je garde sans") 
		return 3, flal
	elseif c > 55 then
		--fluxus:push("Je garde")
		return 2, flal
	elseif c > 40 then 
		--fluxus:push("Je petite")
		return 1, flal
	else
		--fluxus:push("Je passe")
		return 0, flal
	end
end	


function tarot.demander.ecart()
	local ecart = cue.new()
	local CR = 6 -- nbr de Carte Restante a mettre au chien 
	local nbrJoueur = 4
	--fluxus:push("Entré dans tarot.demander.ecart()")
	maMain = tarot.main:getTruthTable() -- récuperation de la main
	
	--[[for i,v in ipairs(maMain) do
		if v then
			--fluxus:push("J'ai le "..tarot.utile.getNom(i))
		else
			--fluxus:push("J'ai n'ai pas le "..tarot.utile.getNom(i))
		end
	end]]
	
		----fluxus:push("Je fais mon écart")
		--local cand = tarot.main:pop(1,#tarot.main)
		----fluxus:push("Candidat "..cand)
		----fluxus:push("soit "..tarot.utile.getNom(cand))
		--[[if tarot.utile.getValeur(cand)<11 then
			----fluxus:push("Je veux ecarter le "..tarot.utile.getNom(cand))
			if maMain[cand] then
				ecart:push(cand)
				CR = CR - 1
				----fluxus:push(tarot.utile.getNom(cand).." est bon pour l'ecart")
			end
		end
		
]]
		
-------------------------------------------FAIRE UNE COUPE ANS TOUS LES CAS------------------------------------------------
------------------------------------------ENLEVE LA COULEUR ON IL Y A LE MOINS DE CARTES
	local min = {}
	local minc1 = 1
	local minc2 = 1
	local max1 = 1
	local max2 = 1
	for i=2,4 do
		if nbrCarteParCouleur[i] < nbrCarteParCouleur[minc1] then
			minc1 = i
		end

	end	
	for i=1,4 do
		if nbrCarteParCouleur[i] > nbrCarteParCouleur[minc1]   then
			if nbrCarteParCouleur[i] < nbrCarteParCouleur[minc2] or minc1 == minc2 then
				minc2 = i
			end
		end
	end
	
	
	for i=2,4 do
		if nbrCarteParCouleur[i] > nbrCarteParCouleur[max1] then
			max1 = i
		end

	end	
	for i=1,4 do
		if nbrCarteParCouleur[i] < nbrCarteParCouleur[max1]   then
			if nbrCarteParCouleur[i] > nbrCarteParCouleur[max2] or max1 == max2 then
				max2 = i
			end
		end
	end

	--fluxus:push("classement des couleur"..minc1..minc2..max2..max1)
	
	minc1 = (minc1 - 1)*14
	minc2 = (minc2 - 1)*14
	max1 = (max1 - 1)*14
	max2 = (max2 - 1)*14
	
	minc[1] = min1 
	minc[2] = min2
	minc[3] = max2
	minc[4] = max1
	
	--fluxus:push("classement des couleur"..minc[1]..minc[2]..minc[3]..minc[4])
]
	
	minc1 = (minc1 - 1)*14
	minc2 = (minc2 - 1)*14
	--fluxus:push("Numero de la couleur que j'ai le moins"..(minc1))
	local Brq
	if maMain[minc1+13+21] and maMain[minc1+14+21] then
	Brq = true
	else
	Brq = false
		for j=1,13 do
			if maMain[minc1+j+21] and CR~=0 then
				--fluxus:push("Je mets "..(minc1+j+21).." à l'écart")
				ecart:push(minc1+j+21)
				CR = CR - 1
				maMain[minc1+j+21] = false
			end
		end
	end
	
	if Brq or nbrAtout > (22/nbrJoueur) then
		for j=1,13 do
			if maMain[minc2+j+21] and CR~=0 then
				--fluxus:push("Je mets "..(minc2+j+21).." à l'écart")
				ecart:push(minc2+j+21)
				CR = CR - 1
				maMain[minc2+j+21] = false
			end
		end
	end

----------------------------------------------------------------------------------------------		
	if CR == 0 then
		return ecart
	end

--------------------------------------------------------------------
		
	if nbrAtout > (22/nbrJoueur) then
--			faire une deuxieme coupe

	end
			

		
	while CR > 0 do
		local rand = math.random(0,3)
		local l = rand*14
		for j=13,1 do
			----fluxus:push("Je vais jeter des cartes de couleur "..l)
			if maMain[l+j+21] and CR~=0 then
				--fluxus:push("Je mets "..(l+j+21).." à l'écart")
				ecart:push(l+j+21)
				CR = CR - 1
				maMain[l+j+21] = false
			end
		end
	end
	return ecart
end




monIA = {}


function tarot.demander.carte()
	if #tarot.main == 18 then -- Premier pli
		--fluxus:push("C'est le premier pli")
		monIA.couleurJouee = {}
		monIA.couleurJouee.pique = false
		monIA.couleurJouee.coeur = false
		monIA.couleurJouee.trefle = false
		monIA.couleurJouee.carreau = false
		monIA.petitTombe = false
		monIA.cartesJouees = {}
		for i=0,77 do
			monIA.cartesJouees[i] = -1 -- -1 pas joué, 0-3 joué par joueur en question
		end
	end
	-- Randomiser l'ordre des couleurs, parce que je peux
	local col = {"pique", "coeur", "trefle", "carreau"}
	local rcol = {}
	while #col>0 do
		local i = math.random(1,#col)
		table.insert(rcol, col[i])
		table.remove(col, i)
	end
	

	-- Identifier le nombre de cartes à chaque couleur
	monIA.cartesACouleur = {}
	monIA.cartesACouleur.atout = tarot.utile.cartesACouleur(tarot.main, "atout")
	monIA.cartesACouleur.pique = tarot.utile.cartesACouleur(tarot.main, "pique")
	monIA.cartesACouleur.coeur = tarot.utile.cartesACouleur(tarot.main, "coeur")
	monIA.cartesACouleur.trefle = tarot.utile.cartesACouleur(tarot.main, "trefle")
	monIA.cartesACouleur.carreau = tarot.utile.cartesACouleur(tarot.main, "carreau")

	table.sort(tarot.legal)
	table.sort(tarot.main)
		
	--for i,v in ipairs(tarot.main) do
	--	--fluxus:push(v)
	--end
	-- Avant dernier tour et j'ai toujours l'excuse, je le BALANCE
	--fluxus:push("J'ai "..#tarot.main.." cartes dans la main, dont "..#tarot.legal.." légales")
	----fluxus:push("Il reste "..#tarot.main.." cartes dans ma main")
	if #tarot.main == 2 and tarot.utile.possede(tarot.legal, 0) then
		--fluxus:push("Je balance l'excuse!")
		return 0
	end
	-- J'ouvre le pli, si preneur = 0 ou 3, ne PAS faire d'ouverture, si preneur = 1 FAIRE des ouvertures
	if tarot.entame == 0 or (tarot.entame == 3 and tarot.pli[1]==0) then
		--fluxus:push("Ma pos est 0")
		-- Ouverture de couleur
		if tarot.preneur == 1 then
			--fluxus:push("Je suis avant le preneur")
			-- Tenter une ouverture
			for i = 1,4 do
				if not monIA.couleurJouee[rcol[i]] then
					fluxus:push("Jet de roi défense")
					fluxus:push(tarot.utile.newCarte(rcol[i],14))
					if tarot.utile.possede(tarot.legal, tarot.utile.newCarte(rcol[i],14)) and monIA.cartesACouleur[rcol[i]]<=3 then
						fluxus:push("Ouaip, ")
						return tarot.utile.newCarte(rcol[i],14)
					end
					--fluxus:push("On n'a pas joué"..rcol[i])
					--fluxus:push(monIA.cartesACouleur[rcol[i]])
					if monIA.cartesACouleur[rcol[i]]>0 then
						--fluxus:push("Je fais une ouverture à "..rcol[i])
						cand = tarot.utile.plusPetitACouleur(tarot.main, rcol[i])
						if tarot.utile.getValeur(cand)<11 then
							return tarot.utile.plusPetitACouleur(tarot.main, rcol[i])
						end
					end
					--fluxus:push("Do I even get here?")
				end
			end
			--fluxus:push("Je joue nawak")
			for i = 1,4 do
				if tarot.utile.plusPetitACouleur(tarot.main, rcol[i]) then
					--fluxus:push("Je joue une carte à "..rcol[i])
					return tarot.utile.plusPetitACouleur(tarot.main, rcol[i])
				end
			end
			--fluxus:push("Je joue une carte légale")
			return tarot.legal[math.random(1,#tarot.main)]

		-- En face du preneur
		elseif tarot.preneur == 2 then
			--fluxus:push("Je suis en face du preneur")

		-- Si je DOIS ouvrir, je fais attention
		elseif tarot.preneur == 3 then
		--fluxus:push("Je suis après le preneur")

		-- Je suis le preneur, si je DOIS ouvrir, je le fais à un roi qui est fort
		else
			--fluxus:push("Je suis le preneur")
			for i = 1,4 do
				if not monIA.couleurJouee[rcol[i]] then
					--fluxus:push("On n'a pas joué"..rcol[i])
					--fluxus:push(monIA.cartesACouleur[rcol[i]])
					fluxus:push("Jet de roi preneur")
					if tarot.utile.possede(tarot.legal, tarot.utile.newCarte(rcol[i],14)) and monIA.cartesACouleur[rcol[i]]<6 then
						return tarot.utile.newCarte(rcol[i],14)
					end
					--fluxus:push("Do I even get here?")
				end
			end
		end
		--fluxus:push("Bleaaargh!")
		carte = tarot.legal[math.random(1,#tarot.legal)]
		--fluxus:push(carte)
		return carte
	end

	-- Observer le pli, identifier la personne remportant ce pli, la couleur, etc...

	-- Quelqu'un joue après moi
	if tarot.entame == 3 or tarot.entame == 2 then
		--fluxus:push("Ma pos est"..tarot.entame)

		-- Jeter une merde mais pas l'excuse
		if tarot.utile.possede(tarot.legal, 0) then
			--fluxus:push("J'ai l'excuse, je joue une merde")
			return tarot.legal[2]
		else
			--fluxus:push("Je joue une merde")
			return tarot.legal[1]
		end
	end

	-- Je termine ce pli, faudrait que je place des points s'ils passent, ou que je tente de remporter le pli si il y a des points dedans et que c'est pas ma team qui a la main, ou que ma team a la main, mais c'est mieux si j'ai la main. Sinon, je tej' de la merde
	if tarot.entame == 1 then
		--fluxus:push("Ma pos est 1")
		-- Si je peux sauver mon petit, je le sauve
		if tarot.utile.possede(tarot.legal, 1) and not tarot.utile.possede(tarot.pli, 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21) then
			--fluxus:push("Je sauve le petit")
			return 1
		end

		-- Jeter une merde mais pas l'excuse ni le petit
		local d = 1
		if tarot.utile.possede(tarot.legal, 1) then
			--fluxus:push("J'ai le petit")
			d = d+1
		end
		if tarot.utile.possede(tarot.legal, 0) then
			--fluxus:push("J'ai l'excuse")
			d = d+1
		end
		--fluxus:push("Je joue ma plus petite carte légale")
		return tarot.legal[d] or tarot.legal[1]
	end

	--fluxus:push("Euh... il y a "..#tarot.pli.." cartes dans le pli")
	return tarot.legal[#tarot.legal]

	-- Comportements "spéciaux"
		-- Jeu du petit

		-- Jeu de l'excuse DONE

		-- Chasse au petit

		-- Ambulance

	-- Identifier les cartes à "proteger" c'est à dire jouer à un moment avec une forte probabilité de garder la carte

		-- Si une carte à proteger a une forte chance de passer, jouer cette carte
	
	-- Si premier du pli
		-- Tenter une ouverture si avant le preneur

		-- Identifier les coupes des joueurs autour de la table
	
			-- Jouer une carte sur une coupe adverse

		-- Jouer une carte basse et sans valeur, à la couleur la plus longue


end



scriptloaded = true
