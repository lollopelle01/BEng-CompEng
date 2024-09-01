%
% Esempio Carrello: risonanza
%
close all; clear all; clc;

% parametri fisici del sistema
mass  = 0.5; % kg (parametro M)
elas  = 2;   % costante elastica [N/m] (parametro k)
smorz = 0.1; % costante dello smorzatore (parametro b) - NON coincide con smorzamento xi

% condizione iniziale pari a 0 (evoluzione libera)
pos_init = 0; % [m]
vel_init = 0; % [m/s]

% calcolo di guadagno, pulsazione naturale e coefficiente di smorzamento
mu = 1/elas; % 1/k
omegan = sqrt(elas/mass); % sqrt(k/M)
xi = smorz/(2*sqrt(elas*mass)); % b/(2 sqrt(kM))

fprintf('Coefficienti sistema:\n');
fprintf('\tGuadagno: %.2f\n', mu);
fprintf('\tPulsazione naturale: %.2f\n', omegan);
fprintf('\tSmorzamento: %.2f\n', xi);

% input applicato: sinusoidale con pulsazione omega_n
inp = @(t) sin(omegan.*t);

% intervallo di tempo
interv = 0:0.1:20; % da 0 a 20 secondi con passo 0.1

%% creazione oggetto sistema e soluzione equazione differenziale

s = tf('s');
G = mu * omegan^2 / (s^2 + 2*xi*omegan*s + omegan^2);

% evoluzione sistema
uu = inp(interv);
x0 = [pos_init; vel_init];
[YY, TT, XX] = lsim(G, uu, interv, x0);

%% plot con animazione

% parametri del disegno
r=0.25; %raggio
Wrad = 0:.02:2*pi;
Wx = r*cos(Wrad);
Wy = r*sin(Wrad);

% inizializza figura
figure(1);
clf;
min_T=0;
max_T=max(TT);
min_Y=min(YY);
max_Y=max(YY);
subplot(2,1,1)
axis([min_T max_T (min_Y-0.2) (max_Y+0.2)])
subplot(2,1,2)
axis([-10 10  0 5])

for i=1:5:length(TT)
    figure(1);
    clf;
    
    % aggiorna figura superiore
    subplot(2,1,1)
    plot(TT(1:i), YY(1:i),'b', TT(1:i), uu(1:i), 'r');
    axis([min_T max_T (min_Y-0.2) (max_Y+0.2)])
    title(['Evoluzione del sistema al tempo: ',num2str(TT(i)),' [s]']);
    legend('Uscita y(t)','Ingresso u(t)')
    grid on; box on;
    
    % aggiorna figura inferiore
    subplot(2,1,2)
    axis([-10 10  0 5])
    patch(YY(i)+[-2 2 2 -2] ,2+[1.5 1.5 -1.5 -1.5],'y') % cart
    hold on;
    patch(Wx+YY(i)-1.8, Wy+.25,'r'); % wheel
    patch(Wx+YY(i)+1.8, Wy+.25,'r'); % wheel
    plot([-10 -10 20],[5 0 0],'k','LineWidth',4) % ground and wall.
    plot([-10, -9, -9:((+9 +YY(i)-4)/9):YY(i)-4, YY(i)-4, YY(i)-2],...
        2+1+[0 0 0 .5 -.5 .5 -.5 .5 -.5 .5 -.5 0 0 0],'r','LineWidth',2) % spring
    title('Risposta del sistema')
    
    % pausa prima del prossimo frame
    pause(0.0001);
end
